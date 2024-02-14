package com.denisardent.catalog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.denisardent.common.Items.ButtonItem
import com.denisardent.common.Product
import com.denisardent.common.translateTagToEnglish
import com.denisardent.domain.GetProductsListUseCase
import com.denisardent.domain.SetLikedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val getProductsListUseCase: GetProductsListUseCase,
    private val setLikedUseCase: SetLikedUseCase
): ViewModel() {

    private val _catalogState = MutableStateFlow<CatalogUiState>(
        CatalogUiState(
            emptyList(),
            emptyList()
        )
    )
    val catalogState: StateFlow<CatalogUiState> = _catalogState.asStateFlow()

    init {
        viewModelScope.launch {
            _catalogState.emit(
                CatalogUiState(
                    getProductsListUseCase(),
                    listOf(
                        ButtonItem("Смотреть все", true),
                        ButtonItem("Лицо"),
                        ButtonItem("Тело"),
                        ButtonItem("Загар"),
                        ButtonItem("Маски")
                    )
                )
            )
        }
    }

    fun changeTag(name: String){
        viewModelScope.launch {
            _catalogState.emit(
                CatalogUiState(
                    productList =
                        if (name == "Смотреть все"){
                            getProductsListUseCase()
                        } else{
                            getProductsListUseCase().filter { product ->
                                product.tags.contains(name.translateTagToEnglish())
                            } },
                    listOf("Смотреть все", "Лицо", "Тело", "Загар", "Маски")
                        .map { tag ->
                            if (name == tag){
                                ButtonItem(tag, true)
                            } else{
                                ButtonItem(tag)
                            }
                        }
                )
            )
        }
    }


    fun setIsLiked(id: String, state: Boolean){
        setLikedUseCase(id, state)
        viewModelScope.launch {
            _catalogState.update {catalogUiState ->
                CatalogUiState(
                    catalogUiState.productList.map {
                        if (it.id == id){
                            it.isLiked = state
                        }
                        it
                    }
                    ,catalogUiState.buttonList)
            }
        }
    }

    fun updateList(){
        viewModelScope.launch {
            _catalogState.update {
                CatalogUiState(getProductsListUseCase(), it.buttonList)
            }
        }
    }

}

data class CatalogUiState(
    val productList: List<Product>,
    val buttonList: List<ButtonItem>
)

