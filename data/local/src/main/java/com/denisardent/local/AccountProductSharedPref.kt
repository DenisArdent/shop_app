package com.denisardent.local

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.denisardent.common.AccountInfo
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

    override fun getInfo(): AccountInfo {
        return AccountInfo(
            preferences.getString(NAME, "") ?: "",
            preferences.getString(SURNAME, "") ?: "",
            preferences.getString(PHONE_NUMBER, "") ?: ""
        )
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
            .remove(NAME)
            .remove(SURNAME)
            .remove(PHONE_NUMBER)
            .apply()
    }

    override fun checkLogined(): Boolean {
        return if (preferences.getString(NAME, null) == null){
            false
        } else {
            Log.d("AAA", "logginned")
            true
        }
    }

    companion object{
        const val APP_PREFERENCES = "APP_PREFERENCES"
        const val NAME = "NAME"
        const val SURNAME = "SURNAME"
        const val PHONE_NUMBER = "PHONE_NUMBER"
    }
}