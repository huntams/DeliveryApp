package com.example.deliveryapp.data

import com.example.deliveryapp.data.remote.model.ApiCategories
import com.example.deliveryapp.data.remote.model.ApiMeals
import retrofit2.http.GET

interface DeliveryApiService {

    @GET("list.php?c=list")
    suspend fun getCategories() : ApiMeals<ApiCategories>
}