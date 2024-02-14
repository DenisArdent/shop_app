package com.denisardent.common

data class Product(
    val id: String,
    val title: String,
    val subtitle: String,
    val price: String,
    val discount: Int,
    val priceWithDiscount: String,
    val unit: String,
    val feedbackCount: Int,
    val rating: Float,
    val tags: List<String>,
    val available: Int,
    val description: String,
    val info: List<InfoData>,
    val ingredients: String,
    val image: List<Int>,
    var isLiked: Boolean = false
)