package com.example.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiMealCompact(
    @SerialName("strMeal")
    val strMeal : String,
    @SerialName("strMealThumb")
    val strMealThumb : String,
    @SerialName("idMeal")
    val idMeal : Int,
)