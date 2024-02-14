package com.denisardent.domain

import com.denisardent.local.AccountPreferences
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val accountPreferences: AccountPreferences
) {
    operator fun invoke(name: String, surName: String, phone: String){
        accountPreferences.login(name, surName, phone)
    }
}