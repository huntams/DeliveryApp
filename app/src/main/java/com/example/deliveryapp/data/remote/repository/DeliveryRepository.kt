package com.example.deliveryapp.data.remote.repository

import com.example.deliveryapp.data.model.Categories
import com.example.deliveryapp.data.remote.model.ApiCategories
import com.example.deliveryapp.data.remote.model.ApiMeals

interface DeliveryRepository {

    suspend fun getCategories() : Categories<ApiCategories>
}