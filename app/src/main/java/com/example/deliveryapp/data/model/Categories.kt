package com.example.deliveryapp.data.model

import com.example.deliveryapp.data.remote.model.ApiMealCompact

data class Categories<T>(
    val meals: List<T>
)