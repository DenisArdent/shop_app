package com.denisardent.network.productsapi

import retrofit2.http.GET

interface ProductsApi {

    @GET("97e721a7-0a66-4cae-b445-83cc0bcf9010")
    suspend fun getProductsList(): ProductsList
}