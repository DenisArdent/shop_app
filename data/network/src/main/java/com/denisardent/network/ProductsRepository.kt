package com.denisardent.network

import com.denisardent.common.Product
import com.denisardent.presentation.R

interface ProductsRepository {
    suspend fun getProductsList(): List<Product>

    companion object{
        val imageMap = mapOf(
            "cbf0c984-7c6c-4ada-82da-e29dc698bb50" to listOf(R.drawable.vox, R.drawable.eveline),
            "54a876a5-2205-48ba-9498-cfecff4baa6e" to listOf(R.drawable.deep, R.drawable.coenzyme),
            "75c84407-52e1-4cce-a73a-ff2d3ac031b3" to listOf(R.drawable.eveline, R.drawable.vox),
            "16f88865-ae74-4b7c-9d85-b68334bb97db" to listOf(R.drawable.deco, R.drawable.coenzyme),
            "26f88856-ae74-4b7c-9d85-b68334bb97db" to listOf(R.drawable.coenzyme, R.drawable.deco),
            "15f88865-ae74-4b7c-9d81-b78334bb97db" to listOf(R.drawable.vox, R.drawable.deep),
            "88f88865-ae74-4b7c-9d81-b78334bb97db" to listOf(R.drawable.coenzyme, R.drawable.deco),
            "55f58865-ae74-4b7c-9d81-b78334bb97db" to listOf(R.drawable.deep, R.drawable.eveline)
        )
    }
}