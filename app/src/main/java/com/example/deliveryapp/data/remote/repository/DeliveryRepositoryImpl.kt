package com.example.deliveryapp.data.remote.repository

import com.example.deliveryapp.data.DeliveryApiService
import com.example.deliveryapp.data.mappers.CategoriesMapper
import com.example.deliveryapp.data.model.Categories
import com.example.deliveryapp.data.remote.model.ApiCategories
import javax.inject.Inject

class DeliveryRepositoryImpl @Inject constructor(
private val deliveryApiService: DeliveryApiService,
private val categoriesMapper: CategoriesMapper,
): DeliveryRepository {
    override suspend fun getCategories(): Categories<ApiCategories> {
        return categoriesMapper.apiToModel(deliveryApiService.getCategories())
    }
}