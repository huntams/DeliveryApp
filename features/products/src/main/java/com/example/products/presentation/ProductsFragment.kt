package com.example.products.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.common.PrefsStorage
import com.example.common.ResultLoader
import com.example.model.Categories
import com.example.network.model.ApiCategories
import com.example.products.R
import com.example.products.databinding.FragmentProductsBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.util.UUID
import javax.inject.Inject
import kotlin.random.Random

@AndroidEntryPoint
class ProductsFragment : Fragment(R.layout.fragment_products) {


    private val binding by viewBinding(FragmentProductsBinding::bind)
    private val viewModel by viewModels<ProductsViewModel>()

    @Inject
    lateinit var categoriesAdapter: CategoriesAdapter

    @Inject
    lateinit var productsAdapter: ProductsAdapter

    @Inject
    lateinit var prefs: PrefsStorage


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var category: String
        val banners = BannersAdapter(List(Random.nextInt(1, 10)) { UUID.randomUUID().toString() })
        var resultCategories: Categories<ApiCategories> = Categories(listOf())
        binding.recyclerViewBanners.apply {
            adapter = banners
        }

        viewModel.categoriesLiveData.observe(viewLifecycleOwner) { result ->
            when (result) {
                is ResultLoader.Success -> {
                    binding.progressBar.visibility = View.GONE
                    categoriesAdapter.apply {
                        //Toast.makeText(requireContext(),result.toString(),Toast.LENGTH_LONG).show()
                        submitList(result.value.meals)
                    }
                    binding.recyclerViewCategories.adapter = categoriesAdapter
                    resultCategories = result.value
                    viewModel.getProductsByCategory(resultCategories)
                }

                is ResultLoader.Loading -> {
                    with(binding) {
                        progressBar.visibility = View.VISIBLE
                    }
                }

                is ResultLoader.Failure -> {
                    Log.e("error", result.throwable.message.toString())
                    val mySnackbar = Snackbar.make(
                        binding.root,
                        getString(R.string.internet_connection),
                        Snackbar.LENGTH_INDEFINITE
                    )
                    mySnackbar.setAction("Reload") {
                        viewModel.getCategories()
                    }
                    mySnackbar.show()
                }

                else -> {}
            }
        }

        viewModel.mealsLiveData.observe(viewLifecycleOwner) { result ->
            when (result) {
                is ResultLoader.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.recyclerViewProducts.visibility = View.VISIBLE
                    category = resultCategories.meals[0].strCategory
                    productsAdapter.submitList(result.value[category])
                    binding.recyclerViewProducts.apply {
                        adapter = productsAdapter
                    }
                }

                is ResultLoader.Failure -> {
                    Log.e("error", result.throwable.message.toString())
                    val mySnackbar = Snackbar.make(
                        binding.root,
                        getString(R.string.internet_connection),
                        Snackbar.LENGTH_INDEFINITE
                    )
                    mySnackbar.setAction("Reload") {
                        viewModel.getProductsByCategory(resultCategories)
                    }
                    mySnackbar.show()
                }

                is ResultLoader.Loading -> {
                    with(binding) {
                        binding.recyclerViewProducts.visibility = View.GONE
                        progressBar.visibility = View.VISIBLE
                    }
                }

                else -> {}
            }
        }

        productsAdapter.setButtonCallback { apiproduct ->

            viewModel.addProductQuantity(
                quantity = apiproduct.countItem,
                orderId = prefs.order,
                productId = apiproduct.idMeal
            )
            viewModel.addProductCrossRef(
                productId = apiproduct.idMeal,
                productQuantityId = apiproduct.idMeal,
            )
        }

        categoriesAdapter.setCallback {
            when (val meals = viewModel.mealsLiveData.value) {
                is ResultLoader.Success -> {
                    category = it.strCategory
                    productsAdapter.submitList(meals.value[category])
                }

                else -> {}
            }
        }
    }

}