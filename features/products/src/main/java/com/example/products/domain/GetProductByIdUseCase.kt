package com.example.products.domain

import com.example.network.model.ApiProduct
import com.example.network.repository.DeliveryRepository
import javax.inject.Inject

class GetProductByIdUseCase @Inject constructor(
    private val deliveryRepository: DeliveryRepository
) {
    suspend operator fun invoke(id: Int): com.example.model.Categories<ApiProduct> {
        return deliveryRepository.getProductById(id)
    }
}