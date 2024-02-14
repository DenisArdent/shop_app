package com.denisardent.network.di

import com.denisardent.network.ProductsRepository
import com.denisardent.network.RetrofitProductsRepository
import com.denisardent.network.productsapi.ProductsApi
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideProductsApi(retrofit: Retrofit): ProductsApi{
        return retrofit.create(ProductsApi::class.java)
    }


}

@Module
@InstallIn(SingletonComponent::class)
interface ProductsNetworkModule{
    @Binds
    fun bindProductsRepository(productsRepository: RetrofitProductsRepository): ProductsRepository
}

const val BASE_URL = "https://run.mocky.io/v3/"