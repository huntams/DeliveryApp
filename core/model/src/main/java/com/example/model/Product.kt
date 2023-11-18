package com.example.model

data class Product(
    val id: Long,
    val nameProduct: String,
    val productCategory: String,
    val productPrice: Int,
    val productInCart: Int = 0,
    val image: String,
)