package com.denisardent.domain

import com.denisardent.local.ProductPreferences
import javax.inject.Inject


class SetLikedUseCase @Inject constructor(private val preferences: ProductPreferences) {
    operator fun invoke(id: String, isLiked: Boolean) {
        preferences.setLiked(id, isLiked)
    }
}