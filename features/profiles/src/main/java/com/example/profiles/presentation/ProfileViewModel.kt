package com.example.profiles.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.db.GetOrdersWithProductsUseCase
import com.example.model.OrderWithProductQuantity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getOrdersWithProductsUseCase: GetOrdersWithProductsUseCase,

    ) : ViewModel() {

    private val _ordersLiveData = MutableStateFlow<List<OrderWithProductQuantity>>(emptyList())
    val ordersLiveData: StateFlow<List<OrderWithProductQuantity>> = _ordersLiveData.asStateFlow()

    fun getOrders() {
        viewModelScope.launch {
            getOrdersWithProductsUseCase().collect { list ->
                _ordersLiveData.value = list.map {
                    it.copy(
                        order = it.order,
                        productQuantity = it.productQuantity,
                    )
                }
            }
        }
    }

}