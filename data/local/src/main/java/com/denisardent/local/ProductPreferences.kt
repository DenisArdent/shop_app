package com.denisardent.local

interface ProductPreferences {
    fun setLiked(id:String, isLiked: Boolean)

    fun getLiked(id: String): Boolean
}