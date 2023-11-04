package com.example.deliveryapp.domain

import com.example.deliveryapp.data.model.Categories
import com.example.deliveryapp.data.remote.model.ApiCategories
import com.example.deliveryapp.data.remote.model.ApiProductCategory
import com.example.deliveryapp.data.remote.repository.DeliveryRepository
import javax.inject.Inject

class GetProductsByCategoryUseCase @Inject constructor(
    private val deliveryRepository: DeliveryRepository
) {
    suspend operator fun invoke(category: String): Categories<ApiProductCategory> {
        return deliveryRepository.getProductsByCategory(category)
    }
}