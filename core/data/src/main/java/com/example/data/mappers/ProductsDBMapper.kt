package com.example.data.mappers

import com.example.database.model.ProductEntity
import com.example.model.Product
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ProductsDBMapper @Inject constructor() {
    fun fromEntityToUIModel(entity: ProductEntity): Product {

        return Product(
            id = entity.id,
            nameProduct = entity.nameProduct,
            productCategory = entity.productCategory,
            productPrice = entity.productPrice,
            productInCart = entity.productInCart,
            image = entity.image,
        )

    }

    fun fromUIModelToEntity(product: Product): ProductEntity {

        return ProductEntity(
            id = product.id,
            nameProduct = product.nameProduct,
            productCategory = product.productCategory,
            productPrice = product.productPrice,
            productInCart = product.productInCart,
            image = product.image,
        )

    }
}