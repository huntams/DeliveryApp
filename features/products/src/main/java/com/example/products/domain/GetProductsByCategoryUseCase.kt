package com.example.products.domain

import com.example.model.Categories
import com.example.network.model.ApiProductCategory
import com.example.network.repository.DeliveryRepository
import javax.inject.Inject

class GetProductsByCategoryUseCase @Inject constructor(
    private val deliveryRepository: DeliveryRepository
) {
    suspend operator fun invoke(category: String): Categories<ApiProductCategory> {
        return deliveryRepository.getProductsByCategory(category)
    }
}