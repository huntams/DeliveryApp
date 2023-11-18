package com.example.products.domain.db

import com.example.database.model.ProductQuantity
import com.example.database.repository.DeliveryDBRepository
import javax.inject.Inject

class AddProductQuantityUseCase @Inject constructor(
    private val deliveryDBRepository: DeliveryDBRepository
) {
    suspend operator fun invoke(productQuantity: ProductQuantity) {
        deliveryDBRepository.addProductQuantity(productQuantity)
    }
}