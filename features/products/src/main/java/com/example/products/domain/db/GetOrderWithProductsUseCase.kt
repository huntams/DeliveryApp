package com.example.products.domain.db

import com.example.database.repository.DeliveryDBRepository
import javax.inject.Inject

class GetOrderWithProductsUseCase @Inject constructor(
    private val deliveryDBRepository: DeliveryDBRepository
) {
    operator fun invoke() {
        deliveryDBRepository.getOrderWithProductQuantity()
    }
}