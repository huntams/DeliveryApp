package com.example.database.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.database.model.OrderEntity
import com.example.database.model.OrderWithProductQuantity
import com.example.database.model.ProductEntity
import com.example.database.model.ProductQuantity
import kotlinx.coroutines.flow.Flow

@Dao
interface DeliveryDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addProduct(product: ProductEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addProducts(products: List<ProductEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addOrder(orderEntity: OrderEntity) : Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addProductQuantity(productQuantity: ProductQuantity)

    @Query("SELECT * FROM Orders")
    fun getOrders(): Flow<List<OrderWithProductQuantity>>
    @Query("SELECT * FROM Products")
    fun getProducts(): Flow<List<ProductEntity>>
}