package com.example.shop

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.database.model.OrderEntity
import com.example.database.model.ProductQuantityEntity
import com.example.domain.db.AddOrderByIdUseCase
import com.example.domain.db.AddOrderDBUseCase
import com.example.domain.db.AddProductCrossRefUseCase
import com.example.domain.db.AddProductQuantityUseCase
import com.example.domain.db.DeleteProductQuantityUseCase
import com.example.domain.db.GetOrderWithProductByIdUseCase
import com.example.domain.db.GetProductsUseCase
import com.example.model.OrderWithProductQuantity
import com.example.model.Product
import com.example.model.ProductQuantity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class ShopViewModel @Inject constructor(
    private val getOrderWithProductByIdUseCase: GetOrderWithProductByIdUseCase,
    private val addProductQuantityUseCase: AddProductQuantityUseCase,
    private val addProductCrossRefUseCase: AddProductCrossRefUseCase,
    private val addOrderByIdUseCase: AddOrderByIdUseCase,
    private val addOrderDBUseCase: AddOrderDBUseCase,
    private val deleteProductQuantityUseCase: DeleteProductQuantityUseCase,
    private val getProductsUseCase: GetProductsUseCase,
) : ViewModel() {

    private val _orderLiveData = MutableLiveData<OrderWithProductQuantity>()
    val orderLiveData: LiveData<OrderWithProductQuantity> = _orderLiveData
    private val _productsLiveData = MutableLiveData<List<Product>>()
    val productsLiveData: LiveData<List<Product>> = _productsLiveData
    private val _idLiveData = MutableLiveData<Long>()
    val idLiveData: LiveData<Long> = _idLiveData


    fun getOrderById(data: Long) {
        viewModelScope.launch {
            getOrderWithProductByIdUseCase(data).collect {
                _orderLiveData.value = it.copy(
                    order = it.order,
                    productQuantity = it.productQuantity,
                )
            }
        }
    }

    fun getTimeDelivery(): List<Long> {
        val times = mutableListOf<Long>()
        var startTime = Date().time + 30_000
        for (i in 1..(Random.nextInt(5, 15))) {
            times.add(startTime)
            startTime += (Random.nextInt(1, 5) * 60_000)
        }
        return times
    }

    fun addOrder() {
        viewModelScope.launch {
            _idLiveData.postValue(addOrderDBUseCase(0) ?: 0)
        }
    }


    fun addOrderById(orderId: Long, totalPrice: Int, coins: Int) {
        viewModelScope.launch {
            addOrderByIdUseCase(
                OrderEntity(
                    orderId = orderId,
                    totalPrice = totalPrice,
                    deliveryCoins = coins
                )
            )
        }
    }

    fun getProducts() {
        viewModelScope.launch {
            getProductsUseCase().collect {
                val data = it.filterIndexed { index, _ -> index % Random.nextInt(5,10) == 0 }
                _productsLiveData.postValue(data)
            }
        }
    }

    fun deleteProductQuantity(productQuantity: ProductQuantity) {
        viewModelScope.launch {
            deleteProductQuantityUseCase(productQuantity)
        }
        getOrderById(productQuantity.orderId)
    }

    fun totalPrice(order: OrderWithProductQuantity): Int {
        return order.productQuantity.sumOf {
            it.product.productPrice * it.productQuantity.quantity
        }
    }

    fun addProductQuantity(quantity: Int, orderId: Long, productId: Long) {
        viewModelScope.launch {
            addProductQuantityUseCase(
                ProductQuantityEntity(
                    productQuantityId = productId,
                    quantity = quantity,
                    orderEntityId = orderId
                )
            )
        }
        getOrderById(orderId)
    }

    fun addProductCrossRef(productId: Long, productQuantityId: Long) {
        viewModelScope.launch {
            addProductCrossRefUseCase(productId, productQuantityId)
        }
    }

}