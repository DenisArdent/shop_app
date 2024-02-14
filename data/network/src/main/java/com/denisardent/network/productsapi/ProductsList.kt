package com.denisardent.network.productsapi

import com.denisardent.common.InfoData

data class ProductsList (
    val items: List<ProductResponse>
)

data class ProductResponse(
    val id: String,
    val title: String,
    val subtitle: String,
    val price: PriceData,
    val feedback: FeedbackData,
    val tags: List<String>,
    val available: Int,
    val description: String,
    val info: List<InfoData>,
    val ingredients: String
)

