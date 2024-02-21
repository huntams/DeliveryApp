package com.example.products.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.common.PrefsStorage
import com.example.common.ResultLoader
import com.example.model.Categories
import com.example.model.Meals
import com.example.model.Product
import com.example.products.R
import com.example.products.databinding.FragmentProductsBinding
import com.google.android.material.chip.Chip
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
    lateinit var productsAdapter: ProductsAdapter

    @Inject
    lateinit var prefs: PrefsStorage

    private val banners by lazy {
        BannersAdapter(List(Random.nextInt(1, 10)) {
            UUID.randomUUID().toString()
        })
    }

    private var category: String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var listProducts = listOf<Product>()
        var resultCategories: Meals<Categories> = Meals(listOf())
        binding.recyclerViewBanners.apply {
            adapter = banners
        }
        binding.recyclerViewProducts.adapter = productsAdapter.apply {
            setCallback {
                Log.e("error", it.throwable.message.toString())
                toastError {
                    viewModel.getCategories()
                }
            }
            setButtonCallback { apiproduct ->

                viewModel.addProductQuantity(
                    quantity = apiproduct.productInCart,
                    orderId = prefs.order,
                    productId = apiproduct.id
                )
                viewModel.addProductCrossRef(
                    productId = apiproduct.id,
                    productQuantityId = apiproduct.id,
                )
            }
        }
        viewModel.categoriesLiveData.observe(viewLifecycleOwner) { result ->
            when (result) {
                is ResultLoader.Success -> {
                    with(binding) {
                        progressBar.visibility = View.GONE
                        result.value.meals.forEach {
                            chipGroup.addView(createTagChip(it.strCategory))
                        }
                        chipGroup.check(binding.chipGroup.children.toList()[0].id)
                    }
                    resultCategories = result.value
                    viewModel.getProducts()
                    viewModel.productsLiveData.observe(viewLifecycleOwner) {
                        listProducts = it
                        viewModel.getProductsByCategory(resultCategories, it)
                    }
                }

                is ResultLoader.Loading -> {

                    binding.progressBar.visibility = View.VISIBLE
                }

                is ResultLoader.Failure -> {
                    Log.e("error", result.throwable.message.toString())
                    toastError { viewModel.getCategories() }
                }

                else -> {}
            }
        }

        viewModel.mealsLiveData.observe(viewLifecycleOwner) { result ->
            when (result) {
                is ResultLoader.Success -> {
                    if (category.isBlank()) {
                        category = resultCategories.meals[0].strCategory
                        productsAdapter.submitList(result.value[category])
                    }
                    binding.progressBar.visibility = View.GONE
                    binding.recyclerViewProducts.visibility = View.VISIBLE
                }

                is ResultLoader.Failure -> {
                    Log.e("error", result.throwable.message.toString())
                    toastError { viewModel.getProductsByCategory(resultCategories, listProducts) }
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


        binding.chipGroup.setOnCheckedStateChangeListener { _, checkedId ->
            if (checkedId.isNotEmpty()) {
                val titleOrNull = binding.chipGroup.findViewById<Chip>(checkedId[0])?.text
                when (val meals = viewModel.mealsLiveData.value) {
                    is ResultLoader.Success -> {
                        category = (titleOrNull).toString()
                        productsAdapter.submitList(meals.value[category])
                    }
                    else -> {}
                }
            }
            else
                binding.chipGroup.check(binding.chipGroup.children.toList()[0].id)
        }
    }
    private fun toastError(block:  () -> Unit){
        binding.progressBar.visibility = View.VISIBLE
        val mySnackbar = Snackbar.make(
            binding.root,
            getString(R.string.internet_connection),
            Snackbar.LENGTH_INDEFINITE
        )
        mySnackbar.setAction(getString(R.string.reload)) {
            block()
        }
        mySnackbar.show()
    }

    private fun createTagChip(chipName: String): Chip {
        return (layoutInflater.inflate(
                com.example.data.R.layout.item_chip_categories,
                binding.chipGroup,
                false
        ) as Chip).apply {
            text = chipName
        }

    }
}
