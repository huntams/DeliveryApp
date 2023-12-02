package com.example.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class OrderEntity(
    @ColumnInfo(name = "orderId")
    @PrimaryKey(autoGenerate = true) val orderId: Long = 0,
    val totalPrice: Int ?= 0,
    val deliveryCoins: Int?=0
)