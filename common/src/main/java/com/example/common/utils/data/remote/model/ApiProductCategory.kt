package com.example.common.utils.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiProductCategory(
    @SerialName("strMeal")
    val strMeal: String,
    @SerialName("strMealThumb")
    val strMealThumb: String,
    @SerialName("idMeal")
    val idMeal: Int,
)