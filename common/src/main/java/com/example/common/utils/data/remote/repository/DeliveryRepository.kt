package com.example.common.utils.data.remote.repository

import com.example.common.utils.data.model.Categories
import com.example.common.utils.data.remote.model.ApiCategories
import com.example.common.utils.data.remote.model.ApiProduct
import com.example.common.utils.data.remote.model.ApiProductCategory

interface DeliveryRepository {

    suspend fun getProductsByCategory(category: String): Categories<ApiProductCategory>
    suspend fun getProductById(id: Int): Categories<ApiProduct>
    suspend fun getCategories(): Categories<ApiCategories>
}