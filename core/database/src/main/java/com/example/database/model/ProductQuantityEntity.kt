package com.example.database.model

import androidx.room.Entity

@Entity(tableName = "product_quantity", primaryKeys = ["productQuantityId", "orderEntityId"])
data class ProductQuantityEntity(
    val productQuantityId: Long,
    val orderEntityId: Long,
    val quantity: Int = 0,
)