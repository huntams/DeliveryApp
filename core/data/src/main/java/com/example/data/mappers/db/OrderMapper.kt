package com.example.data.mappers.db

import com.example.data.mappers.ProductsDBMapper
import com.example.database.model.OrderEntity
import com.example.database.model.OrderWithProductQuantityEntity
import com.example.database.model.ProductQuantityAndProductEntity
import com.example.database.model.ProductQuantityEntity
import com.example.model.Order
import com.example.model.OrderWithProductQuantity
import com.example.model.ProductQuantity
import com.example.model.ProductQuantityAndProduct
import javax.inject.Inject

class OrderMapper @Inject constructor(
    private val productsDBMapper: ProductsDBMapper
) {
    fun fromEntityToUIModel(entity: OrderEntity): Order {
        return Order(
            orderId = entity.orderId,
            totalPrice = entity.totalPrice,
        )
    }

    fun fromOrderWithProductQuantityEntityToUIModel(entity: OrderWithProductQuantityEntity): OrderWithProductQuantity {

        return OrderWithProductQuantity(
            order = fromEntityToUIModel(entity.orderEntity),
            productQuantity = entity.productQuantity.map{
                fromProductQuantityAndProductEntityToModel(it)
            },
        )
    }
    fun fromProductQuantityAndProductEntityToModel(entity: ProductQuantityAndProductEntity): ProductQuantityAndProduct{
        return ProductQuantityAndProduct(
            product = productsDBMapper.fromEntityToUIModel(entity.productEntity),
            productQuantity = fromProductQuantityEntityToUIModel(entity.productQuantityEntity),
        )
    }
    fun fromProductQuantityEntityToUIModel(entity: ProductQuantityEntity): ProductQuantity {

        return ProductQuantity(
            id = entity.productQuantityId,
            quantity = entity.quantity,
            orderId = entity.orderEntityId,
        )
    }
    fun FromUIModelToProductQuantityEntity(productQuantity: ProductQuantity): ProductQuantityEntity {

        return ProductQuantityEntity(
            productQuantityId = productQuantity.id,
            quantity = productQuantity.quantity,
            orderEntityId = productQuantity.orderId,
        )
    }
}