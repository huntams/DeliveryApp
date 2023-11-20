package com.example.domain.db

import com.example.database.repository.DeliveryDBRepository
import com.example.model.OrderWithProductQuantity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetOrderWithProductByIdUseCase @Inject constructor(
    private val deliveryDBRepository: DeliveryDBRepository
) {
    operator fun invoke(data: Long): Flow<OrderWithProductQuantity> {
        return deliveryDBRepository.getOrderWithProductQuantityById(data)
    }
}