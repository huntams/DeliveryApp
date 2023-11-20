package com.example.database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.database.model.OrderEntity
import com.example.database.model.ProductEntity
import com.example.database.model.ProductQuantityCrossRef
import com.example.database.model.ProductQuantityEntity

@Database(entities = [ProductEntity::class,OrderEntity::class,ProductQuantityEntity::class,ProductQuantityCrossRef::class], version = 1)
abstract class DeliveryDB : RoomDatabase() {
    abstract fun deliveryDAO(): DeliveryDAO
}