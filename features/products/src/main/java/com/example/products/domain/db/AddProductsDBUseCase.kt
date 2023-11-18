package com.example.products.domain.db

import com.example.database.model.ProductEntity
import com.example.database.repository.DeliveryDBRepository
import javax.inject.Inject

class AddProductsDBUseCase @Inject constructor(
    private val deliveryDBRepository: DeliveryDBRepository
) {
    suspend operator fun invoke(product: List<ProductEntity>) {
        deliveryDBRepository.addProducts(product)
    }
}