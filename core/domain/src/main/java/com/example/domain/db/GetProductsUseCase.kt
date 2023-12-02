package com.example.domain.db

import com.example.database.repository.DeliveryDBRepository
import com.example.model.Product
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val deliveryDBRepository: DeliveryDBRepository
) {
    operator fun invoke(): Flow<List<Product>> {
        return deliveryDBRepository.getProducts()
    }
}