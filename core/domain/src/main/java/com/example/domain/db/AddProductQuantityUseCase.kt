package com.example.domain.db

import com.example.database.model.ProductQuantityEntity
import com.example.database.repository.DeliveryDBRepository
import javax.inject.Inject

class AddProductQuantityUseCase @Inject constructor(
    private val deliveryDBRepository: DeliveryDBRepository
) {
    suspend operator fun invoke(productQuantityEntity: ProductQuantityEntity): Long {
        return deliveryDBRepository.addProductQuantity(productQuantityEntity)
    }
}