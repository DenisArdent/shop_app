package com.denisardent.local

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccountProductSharedPref @Inject constructor(@ApplicationContext context: Context): ProductPreferences, AccountPreferences {
    private var preferences: SharedPreferences

    init {
        preferences = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
    }

    override fun setLiked(id:String, isLiked: Boolean){
        preferences.edit()
            .putBoolean(id, isLiked)
            .apply()
    }

    override fun getLiked(id: String): Boolean{
        return preferences.getBoolean(id, false)
    }

    override fun login(name: String, surName: String, phone: String){
        preferences.edit()
            .putString(NAME, name)
            .putString(SURNAME, surName)
            .putString(PHONE_NUMBER, phone)
            .apply()
    }

    override fun logout() {
        preferences.edit()
            .putString(NAME, null)
            .putString(SURNAME, null)
            .putString(PHONE_NUMBER, null)
            .apply()
    }

    override fun checkLogined(): Boolean {
        if (preferences.getString(NAME, null) == null){
            return false
        } else {
            return true
        }
    }

    companion object{
        const val APP_PREFERENCES = "APP_PREFERENCES"
        const val NAME = "NAME"
        const val SURNAME = "SURNAME"
        const val PHONE_NUMBER = "PHONE_NUMBER"
    }
}