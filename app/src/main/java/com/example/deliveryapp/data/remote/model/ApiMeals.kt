package com.example.deliveryapp.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiMeals<T>(
    @SerialName("meals")
    val meals: List<T>,
)