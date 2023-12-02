package com.example.model

data class Order(
    val orderId: Long = 0,
    val totalPrice: Int? = 0,
    val deliveryCoins: Int?=0,
)