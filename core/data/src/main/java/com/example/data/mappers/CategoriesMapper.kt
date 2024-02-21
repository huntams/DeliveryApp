package com.example.data.mappers

import com.example.model.Categories
import com.example.model.Meals
import com.example.model.Product
import com.example.model.ProductCategory
import com.example.model.ProductNet
import com.example.network.model.ApiCategories
import com.example.network.model.ApiMeals
import com.example.network.model.ApiProduct
import com.example.network.model.ApiProductCategory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoriesMapper @Inject constructor() {

    fun apiCategoriesToModel(apiMeals: ApiMeals<ApiCategories>) = Meals(
        meals = apiMeals.meals.map {
            Categories(strCategory = it.strCategory)
        }
    )

    fun apiProductsCategoriesToModel(apiMeals: ApiMeals<ApiProductCategory>) =
        Meals(
            meals = apiMeals.meals.map {
                ProductCategory(
                    strMeal = it.strMeal,
                    strMealThumb = it.strMealThumb,
                    idMeal = it.idMeal,
                    price = it.price,
                    countItem = it.countItem
                )
            }
        )

    fun apiProductToModel(apiMeals: ApiMeals<ApiProduct>) = Meals(
        meals = apiMeals.meals.map {
            ProductNet(
                idMeal = it.idMeal,
                strMealThumb = it.strMealThumb,
                strMeal = it.strMeal,
                strCategory = it.strCategory,
                strInstructions = it.strInstructions,
            )
        }
    )
}