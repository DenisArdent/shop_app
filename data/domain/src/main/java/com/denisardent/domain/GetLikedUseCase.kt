package com.denisardent.domain

import com.denisardent.local.ProductPreferences
import javax.inject.Inject

class GetLikedUseCase @Inject constructor(private val preferences: ProductPreferences) {
    operator fun invoke(id: String): Boolean {
        return preferences.getLiked(id)
    }
}