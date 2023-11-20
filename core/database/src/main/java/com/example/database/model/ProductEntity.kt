package com.example.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey val productId: Long,
    val nameProduct: String,
    val productCategory: String,
    val productPrice: Int,
    val productInCart: Int = 0,
    val image: String,
    )