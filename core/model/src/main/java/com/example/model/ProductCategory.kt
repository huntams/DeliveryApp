package com.example.model

import java.text.NumberFormat

data class ProductCategory(
    val strMeal: String,
    val strMealThumb: String,
    val idMeal: Long,
    val price: Int ,
    var countItem: Int = 0,
)