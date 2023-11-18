package com.example.database.repository

import com.example.database.model.OrderWithProductQuantity
import com.example.database.model.ProductEntity
import com.example.database.model.ProductQuantity
import com.example.model.Product
import kotlinx.coroutines.flow.Flow

interface DeliveryDBRepository {

    suspend fun addProduct(product: ProductEntity)

    suspend fun addOrder(totalPrice: Int): Long

    suspend fun addProductQuantity(productQuantity: ProductQuantity)
    suspend fun addProducts(products: List<ProductEntity>)
    fun getOrderWithProductQuantity(): Flow<List<OrderWithProductQuantity>>

    fun getProducts(): Flow<List<Product>>
}