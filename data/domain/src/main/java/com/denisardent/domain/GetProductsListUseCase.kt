package com.denisardent.domain

import com.denisardent.common.Product
import com.denisardent.local.ProductPreferences
import com.denisardent.network.ProductsRepository
import javax.inject.Inject

class GetProductsListUseCase @Inject constructor(
    private val productsRepository: ProductsRepository,
    private val preferences: ProductPreferences
) {
    suspend operator fun invoke(): List<Product>{
       val productsList = productsRepository.getProductsList().toMutableList()
        productsList.forEach {
            it.isLiked = preferences.getLiked(it.id)
        }
        return productsList
    }
}