package com.example.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.random.Random

@Serializable
data class ApiProductCategory(
    @SerialName("strMeal")
    val strMeal: String,
    @SerialName("strMealThumb")
    val strMealThumb: String,
    @SerialName("idMeal")
    val idMeal: Long,
    val price: Int = Random.nextInt(300, 1000),
    var countItem: Int = 0,
)