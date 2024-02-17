package com.denisardent.domain

import com.denisardent.common.AccountInfo
import com.denisardent.local.AccountPreferences
import javax.inject.Inject

class GetAccountInfo@Inject constructor(
    private val accountPreferences: AccountPreferences
) {
    operator fun invoke(): AccountInfo{
        return accountPreferences.getInfo()
    }
}