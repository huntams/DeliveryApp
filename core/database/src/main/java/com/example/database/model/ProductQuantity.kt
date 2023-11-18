package com.example.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "product_quantity", primaryKeys = ["productEntityId", "orderEntityId"])
data class ProductQuantity(
    @ColumnInfo(name = "productEntityId")
    val productEntityId: Long,
    @ColumnInfo(name = "orderEntityId")
    val orderEntityId: Long,
    val quantity: Int = 0,
)