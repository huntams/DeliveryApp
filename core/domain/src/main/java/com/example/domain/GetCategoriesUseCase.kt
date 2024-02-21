package com.example.domain


import com.example.model.Categories
import com.example.model.Meals
import com.example.network.repository.DeliveryRepository
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val deliveryRepository: DeliveryRepository
) {
    suspend operator fun invoke(): Meals<Categories> {
        return deliveryRepository.getCategories()
    }
}