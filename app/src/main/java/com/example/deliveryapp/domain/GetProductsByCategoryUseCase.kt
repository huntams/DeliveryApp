package com.example.deliveryapp.domain

import com.example.deliveryapp.common.data.model.Categories
import com.example.deliveryapp.common.data.remote.model.ApiProductCategory
import com.example.deliveryapp.common.data.remote.repository.DeliveryRepository
import javax.inject.Inject

class GetProductsByCategoryUseCase @Inject constructor(
    private val deliveryRepository: DeliveryRepository
) {
    suspend operator fun invoke(category: String): Categories<ApiProductCategory> {
        return deliveryRepository.getProductsByCategory(category)
    }
}