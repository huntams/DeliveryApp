package com.example.data.repository

import com.example.data.mappers.CategoriesMapper
import com.example.model.Categories
import com.example.network.DeliveryApiService
import com.example.network.model.ApiCategories
import com.example.network.model.ApiProduct
import com.example.network.model.ApiProductCategory
import com.example.network.repository.DeliveryRepository
import javax.inject.Inject

class DeliveryRepositoryImpl @Inject constructor(
    private val deliveryApiService: DeliveryApiService,
    private val categoriesMapper: CategoriesMapper,
) : DeliveryRepository {
    override suspend fun getProductsByCategory(category: String): Categories<ApiProductCategory> {
        return categoriesMapper.apiProductsCategoriesToModel(
            deliveryApiService.getProductsByCategory(
                category
            )
        )
    }

    override suspend fun getProductById(id: Int): Categories<ApiProduct> {
        return categoriesMapper.apiProductToModel(deliveryApiService.getProductById(id))
    }

    override suspend fun getCategories(): Categories<ApiCategories> {
        return categoriesMapper.apiCategoriesToModel(deliveryApiService.getCategories())
    }
}