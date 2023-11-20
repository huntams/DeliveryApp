package com.example.domain.db

import com.example.database.repository.DeliveryDBRepository
import javax.inject.Inject

class AddProductCrossRefUseCase @Inject constructor(
    private val deliveryDBRepository: DeliveryDBRepository
) {
    suspend operator fun invoke(productId: Long, productQuantityId: Long) {
        deliveryDBRepository.addProductCrossRef(productId, productQuantityId)
    }
}