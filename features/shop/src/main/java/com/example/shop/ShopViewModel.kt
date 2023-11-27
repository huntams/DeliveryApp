package com.example.shop

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.database.model.ProductQuantityEntity
import com.example.domain.db.AddProductQuantityUseCase
import com.example.domain.db.DeleteProductQuantityUseCase
import com.example.domain.db.GetOrderWithProductByIdUseCase
import com.example.model.OrderWithProductQuantity
import com.example.model.ProductQuantity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class ShopViewModel @Inject constructor(
    private val getOrderWithProductByIdUseCase: GetOrderWithProductByIdUseCase,
    private val addProductQuantityUseCase: AddProductQuantityUseCase,
    private val deleteProductQuantityUseCase: DeleteProductQuantityUseCase,
) : ViewModel() {
    private val _orderLiveData = MutableLiveData<OrderWithProductQuantity>()
    val orderLiveData: LiveData<OrderWithProductQuantity> = _orderLiveData
    val price: Int = Random.nextInt(300, 1000)
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

    fun deleteProductQuantity(productQuantity: ProductQuantity) {
        viewModelScope.launch {
            deleteProductQuantityUseCase(productQuantity)
        }
        getOrderById(productQuantity.orderId)
    }

    fun totalPrice(order: OrderWithProductQuantity) : Int{
        return order.productQuantity.sumOf {
            it.product.productPrice * it.productQuantity.quantity
        } + price
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
}