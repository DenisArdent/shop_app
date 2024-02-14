package com.denisardent.network.productsapi

data class PriceData(
    val price: String,
    val discount: Int,
    val priceWithDiscount: String,
    val unit: String
)

data class FeedbackData(
    val count: Int,
    val rating: Float
)

