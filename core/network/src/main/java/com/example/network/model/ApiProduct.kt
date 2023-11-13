package com.example.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiProduct(
    @SerialName("idMeal")
    val idMeal : Int,
    @SerialName("strMeal")
    val strMeal: String,
    @SerialName("strCategory")
    val strCategory : String,
    @SerialName("strInstructions")
    val strInstructions: String,
    @SerialName("strMealThumb")
    val strMealThumb: String,
)