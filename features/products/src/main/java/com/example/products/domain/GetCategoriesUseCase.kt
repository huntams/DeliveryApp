package com.example.products.domain

import com.example.common.utils.data.model.Categories
import com.example.common.utils.data.remote.model.ApiCategories
import com.example.common.utils.data.remote.repository.DeliveryRepository
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val deliveryRepository: DeliveryRepository
) {
    suspend operator fun invoke(): Categories<ApiCategories> {
        return deliveryRepository.getCategories()
    }
}