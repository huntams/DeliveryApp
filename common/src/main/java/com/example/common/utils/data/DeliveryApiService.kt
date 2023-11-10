package com.example.common.utils.data

import com.example.common.utils.data.remote.model.ApiCategories
import com.example.common.utils.data.remote.model.ApiMeals
import com.example.common.utils.data.remote.model.ApiProduct
import com.example.common.utils.data.remote.model.ApiProductCategory
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