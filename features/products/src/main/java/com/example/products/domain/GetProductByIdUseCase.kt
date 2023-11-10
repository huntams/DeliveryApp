package com.example.products.domain

import com.example.common.utils.data.model.Categories
import com.example.common.utils.data.remote.model.ApiProduct
import com.example.common.utils.data.remote.repository.DeliveryRepository
import javax.inject.Inject

class GetProductByIdUseCase @Inject constructor(
    private val deliveryRepository: DeliveryRepository
) {
    suspend operator fun invoke(id: Int): Categories<ApiProduct> {
        return deliveryRepository.getProductById(id)
    }
}