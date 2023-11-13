package com.example.products.domain


import com.example.model.Categories
import com.example.network.model.ApiCategories
import com.example.network.repository.DeliveryRepository
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val deliveryRepository: DeliveryRepository
) {
    suspend operator fun invoke(): Categories<ApiCategories> {
        return deliveryRepository.getCategories()
    }
}