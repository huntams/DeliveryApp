package com.example.products.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.common.BaseViewModel
import com.example.common.ResultLoader
import com.example.database.model.ProductEntity
import com.example.database.model.ProductQuantity
import com.example.model.Categories
import com.example.network.model.ApiCategories
import com.example.network.model.ApiProduct
import com.example.network.model.ApiProductCategory
import com.example.products.domain.GetCategoriesUseCase
import com.example.products.domain.GetProductByIdUseCase
import com.example.products.domain.GetProductsByCategoryUseCase
import com.example.products.domain.db.AddOrderDBUseCase
import com.example.products.domain.db.AddProductDBUseCase
import com.example.products.domain.db.AddProductQuantityUseCase
import com.example.products.domain.db.AddProductsDBUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val addProductDBUseCase: AddProductDBUseCase,
    private val addProductQuantityUseCase: AddProductQuantityUseCase,
    private val addOrderDBUseCase: AddOrderDBUseCase,
    private val addProductsDBUseCase: AddProductsDBUseCase,
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

    private val _idLiveData = MutableLiveData<Long>()
    val idLiveData: LiveData<Long> = _idLiveData

    fun addProductQuantity(quantity: Int, orderId: Long, productId: Long) {
        viewModelScope.launch {
            addProductQuantityUseCase(
                ProductQuantity(
                    quantity = quantity, orderEntityId = orderId, productEntityId = productId,
                )
            )
        }
    }

    fun addOrder() {
        viewModelScope.launch {
            _idLiveData.postValue(addOrderDBUseCase(3) ?: 0)
        }
    }

    fun getProductsByCategory(categories: Categories<ApiCategories>) {

        val meals = mutableMapOf<String, List<ApiProductCategory>>()
        viewModelScope.launch {
            try {
                _mealsLiveData.postValue(ResultLoader.Loading())
                for (meal in categories.meals) {
                    getProductsByCategoryUseCase(meal.strCategory).also {
                        meals[meal.strCategory] = it.meals
                    }
                }
                _mealsLiveData.postValue(ResultLoader.Success(meals))
                addProductsDB(meals)
            } catch (t: Throwable) {
                _mealsLiveData.postValue(ResultLoader.Failure(t))
            }

        }
    }

    private suspend fun addProductsDB(categories: MutableMap<String, List<ApiProductCategory>>) {

        val productsEntity = mutableListOf<ProductEntity>()
        categories.forEach { category ->
            for (product in category.value) {
                productsEntity.add(
                    ProductEntity(
                        productCategory = category.key,
                        image = product.strMealThumb,
                        productPrice = product.price,
                        productInCart = product.countItem,
                        nameProduct = product.strMeal,
                        id = product.idMeal,
                    )
                )
            }
        }
        addProductsDBUseCase(productsEntity)
    }

    fun addProductDB(product: ProductEntity) {
        viewModelScope.launch {
            addProductDBUseCase(product)
        }
    }

    fun getProductById(id: Int) {
        viewModelScope.launch {
            getProductByIdUseCase(id).also {
                _productLiveData.postValue(it)
            }
        }
    }


    fun getCategories() {/*
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