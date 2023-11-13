package com.example.network.repository

import com.example.model.Categories
import com.example.network.model.ApiCategories
import com.example.network.model.ApiProduct
import com.example.network.model.ApiProductCategory

interface DeliveryRepository {

    suspend fun getProductsByCategory(category: String): Categories<ApiProductCategory>
    suspend fun getProductById(id: Int): Categories<ApiProduct>
    suspend fun getCategories(): Categories<ApiCategories>
}