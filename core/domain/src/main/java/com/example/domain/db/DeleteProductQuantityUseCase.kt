package com.example.domain.db

import com.example.database.repository.DeliveryDBRepository
import com.example.model.ProductQuantity
import javax.inject.Inject

class DeleteProductQuantityUseCase @Inject constructor(
    private val deliveryDBRepository: DeliveryDBRepository
) {
    suspend operator fun invoke(product: ProductQuantity) {
        deliveryDBRepository.deleteProductQuantity(product)
    }
}