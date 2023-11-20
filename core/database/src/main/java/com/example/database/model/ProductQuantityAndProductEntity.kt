package com.example.database.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class ProductQuantityAndProductEntity(
    @Embedded val productQuantityEntity: ProductQuantityEntity,
    @Relation(
        parentColumn = "productQuantityId",
        entityColumn = "productId",
        associateBy = Junction(ProductQuantityCrossRef::class)
    )
     val productEntity: ProductEntity,
)