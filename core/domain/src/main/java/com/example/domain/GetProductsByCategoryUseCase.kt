package com.example.domain

import com.example.model.Meals
import com.example.model.ProductCategory
import com.example.network.repository.DeliveryRepository
import javax.inject.Inject

class GetProductsByCategoryUseCase @Inject constructor(
    private val deliveryRepository: DeliveryRepository
) {
    suspend operator fun invoke(category: String): Meals<ProductCategory> {
        return deliveryRepository.getProductsByCategory(category)
    }
}