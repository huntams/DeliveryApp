package com.example.database.model

import androidx.room.Embedded
import androidx.room.Relation

data class OrderWithProductQuantityEntity(
    @Embedded val orderEntity: OrderEntity,
    @Relation(
        entity = ProductQuantityEntity::class,
        parentColumn = "orderId",
        entityColumn = "orderEntityId",
    )
    val productQuantity: List<ProductQuantityAndProductEntity>,
)