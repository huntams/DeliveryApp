package com.example.products.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.common.BaseViewModel
import com.example.common.ResultLoader
import com.example.database.model.ProductEntity
import com.example.database.model.ProductQuantityEntity
import com.example.domain.GetCategoriesUseCase
import com.example.domain.GetProductsByCategoryUseCase
import com.example.domain.db.AddProductCrossRefUseCase
import com.example.domain.db.AddProductQuantityUseCase
import com.example.domain.db.AddProductsDBUseCase
import com.example.domain.db.GetProductsUseCase
import com.example.model.Categories
import com.example.model.Product
import com.example.network.model.ApiCategories
import com.example.network.model.ApiProductCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val addProductQuantityUseCase: AddProductQuantityUseCase,
    private val addProductsDBUseCase: AddProductsDBUseCase,
    private val addProductCrossRefUseCase: AddProductCrossRefUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getProductsByCategoryUseCase: GetProductsByCategoryUseCase,
    private val getProductsUseCase: GetProductsUseCase,
) : BaseViewModel() {
    private val _categoriesLiveData = MutableLiveData<ResultLoader<Categories<ApiCategories>>>()
    val categoriesLiveData: LiveData<ResultLoader<Categories<ApiCategories>>> = _categoriesLiveData


    private val _mealsLiveData =
        MutableLiveData<ResultLoader<MutableMap<String, List<ApiProductCategory>>>>()
    val mealsLiveData: LiveData<ResultLoader<MutableMap<String, List<ApiProductCategory>>>> =
        _mealsLiveData


    private val _quantityIdLiveData = MutableLiveData<Long>()

    private val _productsLiveData = MutableLiveData<List<Product>>()
    val productsLiveData: LiveData<List<Product>> = _productsLiveData

    init {
        getCategories()

    }

    fun getProducts() {
        viewModelScope.launch {
            getProductsUseCase().collect { list ->
                val data = list.map { product ->
                    ApiProductCategory(
                        strMeal = product.productCategory,
                        strMealThumb = product.nameProduct,
                        price = product.productPrice,
                        idMeal = product.id,
                        countItem = product.productInCart
                    )
                }
                _productsLiveData.postValue(list)
            }
        }
    }

    fun addProductQuantity(quantity: Int, orderId: Long, productId: Long) {
        viewModelScope.launch {
            _quantityIdLiveData.postValue(
                addProductQuantityUseCase(
                    ProductQuantityEntity(
                        productQuantityId = productId, quantity = quantity, orderEntityId = orderId
                    )
                ) ?: 0
            )
        }
    }

    fun addProductCrossRef(productId: Long, productQuantityId: Long) {
        viewModelScope.launch {
            addProductCrossRefUseCase(productId, productQuantityId)
        }
    }


    fun getProductsByCategory(
        categories: Categories<ApiCategories>,
        list: List<Product>
    ) {

        val meals = mutableMapOf<String, List<ApiProductCategory>>()
        viewModelScope.launch {
            try {
                _mealsLiveData.postValue(ResultLoader.Loading())
                if (list.isEmpty()) {
                    for (meal in categories.meals) {
                        getProductsByCategoryUseCase(meal.strCategory).also {
                            meals[meal.strCategory] = it.meals
                        }
                    }
                    _mealsLiveData.postValue(ResultLoader.Success(meals))
                    addProductsDB(meals)
                } else {
                    for (meal in categories.meals) {
                        val data =
                            list.filter { it.productCategory == meal.strCategory }.map { product ->
                                ApiProductCategory(
                                    strMeal = product.nameProduct,
                                    strMealThumb = product.image,
                                    price = product.productPrice,
                                    idMeal = product.id,
                                    countItem = product.productInCart
                                )
                            }
                        meals[meal.strCategory] = data
                    }
                    _mealsLiveData.postValue(ResultLoader.Success(meals))
                }
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
                        productId = product.idMeal,
                    )
                )
            }
        }
        addProductsDBUseCase(productsEntity)
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