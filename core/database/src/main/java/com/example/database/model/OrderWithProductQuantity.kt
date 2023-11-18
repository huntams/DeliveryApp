package com.example.database.model

import androidx.room.Embedded
import androidx.room.Relation

data class OrderWithProductQuantity(
    @Embedded
    val orderEntity: OrderEntity,
    @Relation(
        parentColumn = "orderId",
        entityColumn = "orderEntityId",
    )
    val productQuantity: List<ProductQuantity>,
)