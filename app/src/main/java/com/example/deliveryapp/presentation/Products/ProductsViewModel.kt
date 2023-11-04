package com.example.deliveryapp.presentation.Products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deliveryapp.data.model.Categories
import com.example.deliveryapp.data.remote.model.ApiCategories
import com.example.deliveryapp.domain.GetCategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
) : ViewModel() {
    private val _categoriesLiveData =MutableLiveData<Categories<ApiCategories>>()
    val categoriesLiveData : LiveData<Categories<ApiCategories>> = _categoriesLiveData


    fun getCategories(){
        viewModelScope.launch {
            getCategoriesUseCase().also {
                _categoriesLiveData.postValue(it)
            }
        }
    }
}