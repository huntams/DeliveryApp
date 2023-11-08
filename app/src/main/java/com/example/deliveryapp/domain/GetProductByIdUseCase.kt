package com.example.deliveryapp.domain

import com.example.deliveryapp.common.data.model.Categories
import com.example.deliveryapp.common.data.remote.model.ApiProduct
import com.example.deliveryapp.common.data.remote.repository.DeliveryRepository
import javax.inject.Inject

class GetProductByIdUseCase @Inject constructor(
    private val deliveryRepository: DeliveryRepository
) {
    suspend operator fun invoke(id: Int): Categories<ApiProduct> {
        return deliveryRepository.getProductById(id)
    }
}