package com.example.model

import java.text.NumberFormat

data class Product(
    val id: Long,
    val nameProduct: String,
    val productCategory: String,
    val productPrice: Int,
    var productInCart: Int = 0,
    val image: String,
){
    fun getFormattedPrice(): String = NumberFormat.getCurrencyInstance().format(productPrice)
}