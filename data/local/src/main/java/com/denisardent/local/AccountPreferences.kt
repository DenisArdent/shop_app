package com.denisardent.local

import com.denisardent.common.AccountInfo

interface AccountPreferences {

    fun getInfo(): AccountInfo

    fun login(name: String, surName: String, phone: String)

    fun logout()

    fun checkLogined(): Boolean
}