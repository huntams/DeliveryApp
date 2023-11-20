package com.example.model


data class OrderWithProductQuantity(
    val order: Order,
    val productQuantity: List<ProductQuantityAndProduct>,
)