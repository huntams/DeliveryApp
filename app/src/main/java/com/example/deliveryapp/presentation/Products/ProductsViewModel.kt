package com.example.deliveryapp.presentation.Products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deliveryapp.base.BaseViewModel
import com.example.deliveryapp.data.model.Categories
import com.example.deliveryapp.data.remote.ResultLoader
import com.example.deliveryapp.data.remote.model.ApiCategories
import com.example.deliveryapp.data.remote.model.ApiProduct
import com.example.deliveryapp.data.remote.model.ApiProductCategory
import com.example.deliveryapp.domain.GetCategoriesUseCase
import com.example.deliveryapp.domain.GetProductByIdUseCase
import com.example.deliveryapp.domain.GetProductsByCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getProductByIdUseCase: GetProductByIdUseCase,
    private val getProductsByCategoryUseCase: GetProductsByCategoryUseCase,
) : BaseViewModel() {
    private val _categoriesLiveData = MutableLiveData<ResultLoader<Categories<ApiCategories>>>()
    val categoriesLiveData: LiveData<ResultLoader<Categories<ApiCategories>>> = _categoriesLiveData


    private val _mealsLiveData = MutableLiveData<ResultLoader<MutableList<ApiProductCategory>>>()
    val mealsLiveData: LiveData<ResultLoader<MutableList<ApiProductCategory>>> =
        _mealsLiveData

    private val _productLiveData = MutableLiveData<Categories<ApiProduct>>()
    val productLiveData: LiveData<Categories<ApiProduct>> = _productLiveData


    fun getProductsByCategory(categories: Categories<ApiCategories>) {

        val data = mutableListOf<ApiProductCategory>()
        viewModelScope.launch {
            try {
                for (meal in categories.meals) {
                    getProductsByCategoryUseCase(meal.strCategory).also {

                        data.addAll(it.meals)
                    }
                }
                _mealsLiveData.postValue(ResultLoader.Success(data))
            } catch (t: Throwable) {
                _mealsLiveData.postValue(ResultLoader.Failure(t))
            }
        }
    }

    fun getProductById(id: Int) {
        viewModelScope.launch {
            getProductByIdUseCase(id).also {
                _productLiveData.postValue(it)
            }
        }
    }


    fun getCategories() {
        /*
        _categoriesLiveData.loadData {
            getCategoriesUseCase()
        }

         */

        viewModelScope.launch {
            try {
                getCategoriesUseCase().also {
                    _categoriesLiveData.postValue(ResultLoader.Success(it))
                }
            } catch (t: Throwable) {
                _categoriesLiveData.postValue(ResultLoader.Failure(t))
            }
        }


    }
}