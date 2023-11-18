package com.example.data.repository

import com.example.data.mappers.ProductsDBMapper
import com.example.database.db.DeliveryDAO
import com.example.database.model.OrderEntity
import com.example.database.model.OrderWithProductQuantity
import com.example.database.model.ProductEntity
import com.example.database.model.ProductQuantity
import com.example.database.repository.DeliveryDBRepository
import com.example.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class DeliveryDBRepositoryImpl @Inject constructor(
    private val deliveryDAO: DeliveryDAO,
    private val productsDBMapper: ProductsDBMapper
) : DeliveryDBRepository {

    override suspend fun addProduct(product: ProductEntity) {
        deliveryDAO.addProduct(product)
    }

    override suspend fun addProducts(products: List<ProductEntity>) {
        deliveryDAO.addProducts(products)
    }

    override suspend fun addOrder(totalPrice: Int): Long {
        return deliveryDAO.addOrder(OrderEntity(totalPrice = totalPrice))
    }

    override suspend fun addProductQuantity(productQuantity: ProductQuantity) {
        deliveryDAO.addProductQuantity(productQuantity)
    }

    override fun getOrderWithProductQuantity(): Flow<List<OrderWithProductQuantity>> {
        return deliveryDAO.getOrders()
    }

    override fun getProducts(): Flow<List<Product>> {
        return deliveryDAO.getProducts().map { list ->
            list.map {
                productsDBMapper.fromEntityToUIModel(it)
            }
        }
    }
}