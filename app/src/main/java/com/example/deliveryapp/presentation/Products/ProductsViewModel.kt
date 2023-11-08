package com.example.deliveryapp.presentation.Products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.deliveryapp.base.BaseViewModel
import com.example.deliveryapp.common.data.model.Categories
import com.example.deliveryapp.common.data.remote.ResultLoader
import com.example.deliveryapp.common.data.remote.model.ApiCategories
import com.example.deliveryapp.common.data.remote.model.ApiProduct
import com.example.deliveryapp.common.data.remote.model.ApiProductCategory
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


    private val _mealsLiveData =
        MutableLiveData<ResultLoader<MutableMap<String, List<ApiProductCategory>>>>()
    val mealsLiveData: LiveData<ResultLoader<MutableMap<String, List<ApiProductCategory>>>> =
        _mealsLiveData

    private val _productLiveData = MutableLiveData<Categories<ApiProduct>>()
    val productLiveData: LiveData<Categories<ApiProduct>> = _productLiveData


    fun getProductsByCategory(categories: Categories<ApiCategories>) {

        val meals = mutableMapOf<String, List<ApiProductCategory>>()
        val data = mutableListOf<ApiProductCategory>()
        viewModelScope.launch {
            try {
                _mealsLiveData.postValue(ResultLoader.Loading())
                for (meal in categories.meals) {
                    getProductsByCategoryUseCase(meal.strCategory).also {

                        data.addAll(it.meals)
                        meals[meal.strCategory] = it.meals
                    }
                }
                _mealsLiveData.postValue(ResultLoader.Success(meals))
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
                _categoriesLiveData.postValue(ResultLoader.Loading())
                getCategoriesUseCase().also {
                    _categoriesLiveData.postValue(ResultLoader.Success(it))
                }
            } catch (t: Throwable) {
                _categoriesLiveData.postValue(ResultLoader.Failure(t))
            }
        }


    }
}