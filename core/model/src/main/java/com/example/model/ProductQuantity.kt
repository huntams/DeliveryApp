package com.example.model

data class ProductQuantity(
    val id: Long,
    val orderId: Long,
    val quantity: Int = 0,

)