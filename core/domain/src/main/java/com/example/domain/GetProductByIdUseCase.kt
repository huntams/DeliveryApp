package com.example.domain

import com.example.model.Meals
import com.example.model.ProductNet
import com.example.network.repository.DeliveryRepository
import javax.inject.Inject

class GetProductByIdUseCase @Inject constructor(
    private val deliveryRepository: DeliveryRepository
) {
    suspend operator fun invoke(id: Int): Meals<ProductNet> {
        return deliveryRepository.getProductById(id)
    }
}