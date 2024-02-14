package com.denisardent.product

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.denisardent.common.Product
import com.denisardent.domain.GetProductByIdUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductViewModel @AssistedInject constructor(
    private val getProductByIdUseCase: GetProductByIdUseCase,
    @Assisted private val productId: String
): ViewModel() {
    private val _productState = MutableStateFlow<Product?>(null)
    val productState = _productState.asStateFlow()

    init {
        viewModelScope.launch {
            _productState.emit(getProductByIdUseCase(productId))
        }
    }


    @AssistedFactory
    interface Factory {
        fun create(productId: String): ProductViewModel
    }

    companion object{
        @Suppress("UNCHECKED_CAST")
        fun provideProductViewModelFactory(factory: Factory, productId: String): ViewModelProvider.Factory{
            return object : ViewModelProvider.Factory{
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return factory.create(productId) as T
                }
            }
        }
    }
}