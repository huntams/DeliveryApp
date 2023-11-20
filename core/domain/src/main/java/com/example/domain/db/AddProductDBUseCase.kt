package com.example.domain.db

import com.example.database.model.ProductEntity
import com.example.database.repository.DeliveryDBRepository
import javax.inject.Inject

class AddProductDBUseCase @Inject constructor(
    private val deliveryDBRepository: DeliveryDBRepository
) {
    suspend operator fun invoke(product: ProductEntity) {
        deliveryDBRepository.addProduct(product)
    }
}