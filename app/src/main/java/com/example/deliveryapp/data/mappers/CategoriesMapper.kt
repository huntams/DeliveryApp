package com.example.deliveryapp.data.mappers

import com.example.deliveryapp.data.model.Categories
import com.example.deliveryapp.data.remote.model.ApiCategories
import com.example.deliveryapp.data.remote.model.ApiMeals
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoriesMapper @Inject constructor() {

    fun apiToModel(apiMeals: ApiMeals<ApiCategories>) = Categories(
        meals = apiMeals.meals
        )
}