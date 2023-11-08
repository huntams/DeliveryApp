package com.example.deliveryapp.domain

import com.example.deliveryapp.common.data.model.Categories
import com.example.deliveryapp.common.data.remote.model.ApiCategories
import com.example.deliveryapp.common.data.remote.repository.DeliveryRepository
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val deliveryRepository: DeliveryRepository
) {
    suspend operator fun invoke(): Categories<ApiCategories> {
        return deliveryRepository.getCategories()
    }
}