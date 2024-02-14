package com.denisardent.domain

import com.denisardent.local.AccountPreferences
import javax.inject.Inject

class CheckLoggedUseCase@Inject constructor(
    private val accountPreferences: AccountPreferences
) {
    operator fun invoke():Boolean{
        return accountPreferences.checkLogined()
    }
}