package com.denisardent.domain

import com.denisardent.local.AccountPreferences
import javax.inject.Inject

class LogoutUseCase@Inject constructor(
    private val accountPreferences: AccountPreferences
) {
    operator fun invoke(){
        accountPreferences.logout()
    }
}