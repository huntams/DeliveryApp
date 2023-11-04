package com.example.deliveryapp.data.remote.repository

import com.example.deliveryapp.data.model.Categories
import com.example.deliveryapp.data.remote.model.ApiCategories
import com.example.deliveryapp.data.remote.model.ApiMeals
import com.example.deliveryapp.data.remote.model.ApiProduct
import com.example.deliveryapp.data.remote.model.ApiProductCategory

interface DeliveryRepository {

    suspend fun getProductsByCategory(category: String): Categories<ApiProductCategory>
    suspend fun getProductById(id: Int): Categories<ApiProduct>
    suspend fun getCategories() : Categories<ApiCategories>
}