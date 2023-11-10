package com.example.common.utils.data.remote.repository

import com.example.common.utils.data.DeliveryApiService
import com.example.common.utils.data.mappers.CategoriesMapper
import com.example.common.utils.data.model.Categories
import com.example.common.utils.data.remote.model.ApiCategories
import com.example.common.utils.data.remote.model.ApiProduct
import com.example.common.utils.data.remote.model.ApiProductCategory
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