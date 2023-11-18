package com.example.database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.database.model.OrderEntity
import com.example.database.model.ProductEntity
import com.example.database.model.ProductQuantity

@Database(entities = [ProductEntity::class,OrderEntity::class,ProductQuantity::class], version = 1)
abstract class DeliveryDB : RoomDatabase() {
    abstract fun deliveryDAO(): DeliveryDAO
}