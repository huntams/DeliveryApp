package com.example.database.repository

import com.example.database.model.ProductEntity
import com.example.database.model.ProductQuantityEntity
import com.example.model.OrderWithProductQuantity
import com.example.model.Product
import com.example.model.ProductQuantity
import kotlinx.coroutines.flow.Flow

interface DeliveryDBRepository {

    suspend fun addProduct(product: ProductEntity)

    suspend fun addOrder(totalPrice: Int): Long

    suspend fun addProductQuantity(productQuantityEntity: ProductQuantityEntity): Long
    suspend fun addProducts(products: List<ProductEntity>)

    suspend fun addProductCrossRef(productId: Long, productQuantity: Long)
    suspend fun deleteProductQuantity(productQuantity: ProductQuantity)

    fun getOrderWithProductQuantityById(data: Long): Flow<OrderWithProductQuantity>
    fun getOrdersWithProductQuantity(): Flow<List<OrderWithProductQuantity>>

    fun getProducts(): Flow<List<Product>>
}