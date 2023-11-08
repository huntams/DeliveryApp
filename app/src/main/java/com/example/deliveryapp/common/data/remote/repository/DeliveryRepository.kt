package com.example.deliveryapp.common.data.remote.repository

import com.example.deliveryapp.common.data.model.Categories
import com.example.deliveryapp.common.data.remote.model.ApiCategories
import com.example.deliveryapp.common.data.remote.model.ApiProduct
import com.example.deliveryapp.common.data.remote.model.ApiProductCategory

interface DeliveryRepository {

    suspend fun getProductsByCategory(category: String): Categories<ApiProductCategory>
    suspend fun getProductById(id: Int): Categories<ApiProduct>
    suspend fun getCategories(): Categories<ApiCategories>
}