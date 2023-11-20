package com.example.database.model

import androidx.room.Entity


@Entity(primaryKeys = ["productId", "productQuantityId"])
data class ProductQuantityCrossRef(
    val productQuantityId: Long,
    val productId: Long,
)