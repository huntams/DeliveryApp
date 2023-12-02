package com.example.domain.db

import com.example.database.model.OrderEntity
import com.example.database.repository.DeliveryDBRepository
import javax.inject.Inject

class AddOrderByIdUseCase @Inject constructor(
    private val deliveryDBRepository: DeliveryDBRepository
) {
    suspend operator fun invoke(orderEntity: OrderEntity): Long {
        return deliveryDBRepository.addOrderById(orderEntity)
    }
}