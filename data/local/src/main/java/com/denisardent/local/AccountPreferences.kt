package com.denisardent.local

interface AccountPreferences {
    fun login(name: String, surName: String, phone: String)

    fun logout()

    fun checkLogined(): Boolean
}