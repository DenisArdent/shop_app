package com.denisardent.local.di

import com.denisardent.local.AccountPreferences
import com.denisardent.local.ProductPreferences
import com.denisardent.local.AccountProductSharedPref
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface LocalModule{
    @Binds
    fun bindProductsRepository(productPreferences: AccountProductSharedPref): ProductPreferences

    @Binds
    fun bindAccountRepository(accountPreferences: AccountProductSharedPref): AccountPreferences
}