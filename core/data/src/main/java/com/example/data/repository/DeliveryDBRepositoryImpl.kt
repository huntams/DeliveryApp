package com.example.data.repository

import com.example.data.mappers.ProductsDBMapper
import com.example.data.mappers.db.OrderMapper
import com.example.database.db.DeliveryDAO
import com.example.database.model.OrderEntity
import com.example.database.model.ProductEntity
import com.example.database.model.ProductQuantityCrossRef
import com.example.database.model.ProductQuantityEntity
import com.example.database.repository.DeliveryDBRepository
import com.example.model.OrderWithProductQuantity
import com.example.model.Product
import com.example.model.ProductQuantity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class DeliveryDBRepositoryImpl @Inject constructor(
    private val deliveryDAO: DeliveryDAO,
    private val productsDBMapper: ProductsDBMapper,
    private val orderMapper: OrderMapper,
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
    override suspend fun addOrderById(orderEntity: OrderEntity): Long {
        return deliveryDAO.addOrderById(orderEntity)
    }

    override suspend fun addProductQuantity(productQuantityEntity: ProductQuantityEntity): Long {
        return deliveryDAO.addProductQuantity(productQuantityEntity)
    }

    override suspend fun deleteProductQuantity(productQuantity: ProductQuantity) {
        deliveryDAO.deleteProductQuantity(
            orderMapper.FromUIModelToProductQuantityEntity(
                productQuantity
            )
        )
    }

    override suspend fun addProductCrossRef(productId: Long, productQuantity: Long) {
        deliveryDAO.addProductCrossRef(ProductQuantityCrossRef(productId, productQuantity))
    }

    override fun getOrderWithProductQuantityById(data: Long): Flow<OrderWithProductQuantity> {
        return deliveryDAO.getOrder(data).map {
            orderMapper.fromOrderWithProductQuantityEntityToUIModel(it)
        }
    }

    override fun getOrdersWithProductQuantity(): Flow<List<OrderWithProductQuantity>> {
        return deliveryDAO.getOrders().map { list ->
            list.map {
                orderMapper.fromOrderWithProductQuantityEntityToUIModel(it)
            }

        }
    }

    override fun getProducts(): Flow<List<Product>> {
        return deliveryDAO.getProducts().map { list ->
            list.map {
                productsDBMapper.fromEntityToUIModel(it)
            }
        }
    }
}