package com.example.network.repository

import com.example.model.Categories
import com.example.model.Meals
import com.example.model.ProductCategory
import com.example.model.ProductNet

interface DeliveryRepository {

    suspend fun getProductsByCategory(category: String): Meals<ProductCategory>
    suspend fun getProductById(id: Int): Meals<ProductNet>
    suspend fun getCategories(): Meals<Categories>
}