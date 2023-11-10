package com.example.products.domain
import com.example.common.utils.data.model.Categories
import com.example.common.utils.data.remote.model.ApiProductCategory
import com.example.common.utils.data.remote.repository.DeliveryRepository
import javax.inject.Inject
class GetProductsByCategoryUseCase @Inject constructor(
    private val deliveryRepository: DeliveryRepository
) {
    suspend operator fun invoke(category: String): Categories<ApiProductCategory> {
        return deliveryRepository.getProductsByCategory(category)
    }
}