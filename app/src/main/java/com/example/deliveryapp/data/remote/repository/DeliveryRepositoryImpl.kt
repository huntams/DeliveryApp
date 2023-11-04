package com.example.deliveryapp.data.remote.repository

import com.example.deliveryapp.data.DeliveryApiService
import com.example.deliveryapp.data.mappers.CategoriesMapper
import com.example.deliveryapp.data.model.Categories
import com.example.deliveryapp.data.remote.model.ApiCategories
import com.example.deliveryapp.data.remote.model.ApiProduct
import com.example.deliveryapp.data.remote.model.ApiProductCategory
import javax.inject.Inject

class DeliveryRepositoryImpl @Inject constructor(
private val deliveryApiService: DeliveryApiService,
private val categoriesMapper: CategoriesMapper,
): DeliveryRepository {
    override suspend fun getProductsByCategory(category: String): Categories<ApiProductCategory> {
        return categoriesMapper.apiProductsCategoriesToModel(deliveryApiService.getProductsByCategory(category))
    }

    override suspend fun getProductById(id: Int): Categories<ApiProduct> {
        return categoriesMapper.apiProductToModel(deliveryApiService.getProductById(id))
    }

    override suspend fun getCategories(): Categories<ApiCategories> {
        return categoriesMapper.apiCategoriesToModel(deliveryApiService.getCategories())
    }
}