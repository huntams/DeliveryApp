package com.example.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiCategories(
    @SerialName("strCategory")
    val strCategory : String,
)