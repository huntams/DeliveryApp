package com.example.database.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.database.model.OrderEntity
import com.example.database.model.OrderWithProductQuantityEntity
import com.example.database.model.ProductEntity
import com.example.database.model.ProductQuantityCrossRef
import com.example.database.model.ProductQuantityEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DeliveryDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addProduct(product: ProductEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addProducts(products: List<ProductEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addOrder(orderEntity: OrderEntity): Long
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addOrderById(orderEntity: OrderEntity): Long

    @Delete
    suspend fun deleteProductQuantity(productQuantity: ProductQuantityEntity)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addProductQuantity(productQuantityEntity: ProductQuantityEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addProductCrossRef(productQuantityCrossRef: ProductQuantityCrossRef)

    @Transaction
    @Query("SELECT * FROM orders")
    fun getOrders(): Flow<List<OrderWithProductQuantityEntity>>

    @Transaction
    @Query("SELECT * FROM orders WHERE orderId LIKE :data")
    fun getOrder(data: Long): Flow<OrderWithProductQuantityEntity>

    @Query("SELECT * FROM products")
    fun getProducts(): Flow<List<ProductEntity>>
}