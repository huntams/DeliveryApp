package com.example.data.repository

import com.example.data.mappers.CategoriesMapper
import com.example.model.Categories
import com.example.model.Meals
import com.example.model.ProductCategory
import com.example.model.ProductNet
import com.example.network.DeliveryApiService
import com.example.network.repository.DeliveryRepository
import javax.inject.Inject

class DeliveryRepositoryImpl @Inject constructor(
    private val deliveryApiService: DeliveryApiService,
    private val categoriesMapper: CategoriesMapper,
) : DeliveryRepository {
    override suspend fun getProductsByCategory(category: String): Meals<ProductCategory> {
        return categoriesMapper.apiProductsCategoriesToModel(
            deliveryApiService.getProductsByCategory(
                category
            )
        )
    }

    override suspend fun getProductById(id: Int): Meals<ProductNet> {
        return categoriesMapper.apiProductToModel(deliveryApiService.getProductById(id))
    }

    override suspend fun getCategories(): Meals<Categories> {
        return categoriesMapper.apiCategoriesToModel(deliveryApiService.getCategories())
    }
}