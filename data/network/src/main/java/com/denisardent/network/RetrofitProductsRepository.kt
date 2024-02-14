package com.denisardent.network

import android.util.Log
import com.denisardent.common.Product
import com.denisardent.network.productsapi.ProductsApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RetrofitProductsRepository @Inject constructor(
    private val productsApi: ProductsApi
): ProductsRepository {
    override suspend fun getProductsList(): List<Product> {
        return withContext(Dispatchers.IO){
            productsApi.getProductsList().items.map { productResponse ->
                Product(
                    id = productResponse.id,
                    title = productResponse.title,
                    subtitle = productResponse.subtitle,
                    price = productResponse.price.price,
                    discount = productResponse.price.discount,
                    priceWithDiscount = productResponse.price.priceWithDiscount,
                    unit = productResponse.price.unit,
                    feedbackCount = productResponse.feedback.count,
                    rating = productResponse.feedback.rating,
                    tags = productResponse.tags,
                    available = productResponse.available,
                    description = productResponse.description,
                    info = productResponse.info,
                    ingredients = productResponse.ingredients,
                    image = ProductsRepository.imageMap.get(productResponse.id) ?: listOf()
                )
            }
        }
    }
}