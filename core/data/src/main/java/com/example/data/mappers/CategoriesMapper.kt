package com.example.data.mappers

import com.example.model.Categories
import com.example.network.model.ApiCategories
import com.example.network.model.ApiMeals
import com.example.network.model.ApiProduct
import com.example.network.model.ApiProductCategory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoriesMapper @Inject constructor() {

    fun apiCategoriesToModel(apiMeals: ApiMeals<ApiCategories>) = Categories(
        meals = apiMeals.meals
    )

    fun apiProductsCategoriesToModel(apiMeals: ApiMeals<ApiProductCategory>) =
        Categories(
            meals = apiMeals.meals
        )

    fun apiProductToModel(apiMeals: ApiMeals<ApiProduct>) = Categories(
        meals = apiMeals.meals
    )
}