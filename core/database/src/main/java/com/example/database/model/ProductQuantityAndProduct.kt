package com.example.database.model

import androidx.room.Embedded
import androidx.room.Relation

data class ProductQuantityAndProduct(
    @Embedded
    val productEntity: ProductEntity,
    @Relation(
        parentColumn = "productId",
        entityColumn = "productEntityId",
    )
    val productQuantity: ProductQuantity,
)