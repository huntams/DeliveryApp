package com.example.network

import com.example.network.model.ApiCategories
import com.example.network.model.ApiMeals
import com.example.network.model.ApiProduct
import com.example.network.model.ApiProductCategory
import retrofit2.http.GET
import retrofit2.http.Query

interface DeliveryApiService {

    @GET("filter.php")
    suspend fun getProductsByCategory(
        @Query("c") category: String
    ): ApiMeals<ApiProductCategory>

    @GET("lookup.php")
    suspend fun getProductById(
        @Query("i") id: Int
    ): ApiMeals<ApiProduct>

    @GET("list.php?c=list")
    suspend fun getCategories(): ApiMeals<ApiCategories>
}