package com.example.domain.db

import com.example.database.repository.DeliveryDBRepository
import com.example.model.OrderWithProductQuantity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetOrdersWithProductsUseCase @Inject constructor(
    private val deliveryDBRepository: DeliveryDBRepository
) {
    operator fun invoke(): Flow<List<OrderWithProductQuantity>> {
        return deliveryDBRepository.getOrdersWithProductQuantity()
    }
}