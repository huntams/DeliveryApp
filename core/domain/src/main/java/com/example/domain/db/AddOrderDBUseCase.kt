package com.example.domain.db

import com.example.database.repository.DeliveryDBRepository
import javax.inject.Inject

class AddOrderDBUseCase @Inject constructor(
    private val deliveryDBRepository: DeliveryDBRepository
) {
    suspend operator fun invoke(totalPrice: Int): Long {
        return deliveryDBRepository.addOrder(totalPrice)
    }
}