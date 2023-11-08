package com.example.deliveryapp.common.data.mappers

import com.example.deliveryapp.common.data.model.Categories
import com.example.deliveryapp.common.data.remote.model.ApiCategories
import com.example.deliveryapp.common.data.remote.model.ApiMeals
import com.example.deliveryapp.common.data.remote.model.ApiProduct
import com.example.deliveryapp.common.data.remote.model.ApiProductCategory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoriesMapper @Inject constructor() {

    fun apiCategoriesToModel(apiMeals: ApiMeals<ApiCategories>) = Categories(
        meals = apiMeals.meals
        )
    fun apiProductsCategoriesToModel(apiMeals: ApiMeals<ApiProductCategory>) = Categories(
        meals = apiMeals.meals
    )
    fun apiProductToModel(apiMeals: ApiMeals<ApiProduct>) = Categories(
        meals = apiMeals.meals
    )
}