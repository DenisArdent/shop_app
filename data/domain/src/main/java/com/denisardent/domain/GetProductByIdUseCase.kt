package com.denisardent.domain

import com.denisardent.common.Product
import javax.inject.Inject

class GetProductByIdUseCase @Inject constructor(private val getProductsListUseCase: GetProductsListUseCase) {
    suspend operator fun invoke(id: String): Product{
        return getProductsListUseCase().first { it.id == id }
    }
}