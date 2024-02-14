package com.denisardent.catalog.liked

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.denisardent.common.Product
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
class LikedViewModel @Inject constructor(
    private val getProductsListUseCase: GetProductsListUseCase,
    private val setLikedUseCase: SetLikedUseCase
): ViewModel() {

    private val _likedState = MutableStateFlow<List<Product>>(
        emptyList()
    )
    val likedState: StateFlow<List<Product>> = _likedState.asStateFlow()

    init {
        viewModelScope.launch {
            _likedState.emit(
                getProductsListUseCase().filter { it.isLiked }
            )
        }
    }


    fun setIsLiked(id: String, state: Boolean){
        setLikedUseCase(id, state)
        viewModelScope.launch {
            _likedState.update { likedState ->
                likedState.map {
                    if (it.id == id){
                        it.isLiked = state
                    }
                    it
                }
            }
        }
    }

    fun updateList(){
        viewModelScope.launch {
            _likedState.update {
                getProductsListUseCase().filter { it.isLiked }
            }
        }
    }
}