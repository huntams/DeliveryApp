package com.example.deliveryapp.presentation.Products

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.deliveryapp.R
import com.example.deliveryapp.data.model.Categories
import com.example.deliveryapp.data.remote.ResultLoader
import com.example.deliveryapp.data.remote.model.ApiCategories
import com.example.deliveryapp.data.remote.model.ApiProductCategory
import com.example.deliveryapp.databinding.FragmentProductsBinding
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
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val banners = BannersAdapter(List(Random.nextInt(1, 10)) { UUID.randomUUID().toString() })
        var resultCategories : Categories<ApiCategories> = Categories(listOf())
        var meals = mutableListOf<ApiProductCategory>()
        binding.recyclerViewBanners.apply {
            adapter = banners
        }


        viewModel.getCategories()
        viewModel.categoriesLiveData.observe(viewLifecycleOwner) {result ->

            when (result) {
                is ResultLoader.Success -> {
                    categoriesAdapter.apply {
                        //Toast.makeText(requireContext(),result.toString(),Toast.LENGTH_LONG).show()
                        submitList(result.value.meals)
                    }
                    binding.recyclerViewTest.apply {
                        adapter = categoriesAdapter
                    }
                    resultCategories = result.value
                    viewModel.getProductsByCategory(result.value)

                }

                is ResultLoader.Failure ->{
                    val mySnackbar = Snackbar.make(binding.root,
                        getString(R.string.internet_connection), Snackbar.LENGTH_INDEFINITE)
                    mySnackbar.setAction("Reload"){
                        viewModel.getCategories()
                    }
                    mySnackbar.show()
                }

                else -> {

                }
            }

        }

        viewModel.mealsLiveData.observe(viewLifecycleOwner) { result ->
            when (result) {
                is ResultLoader.Success -> {
                    productsAdapter.submitList (result.value)
                    binding.recyclerView.apply {
                        adapter = productsAdapter
                    }
                }
                is ResultLoader.Failure -> {
                    val mySnackbar = Snackbar.make(binding.root,
                        getString(R.string.internet_connection), Snackbar.LENGTH_INDEFINITE)
                    mySnackbar.setAction("Reload"){
                        viewModel.getProductsByCategory(resultCategories)
                    }
                    mySnackbar.show()
                }
                else -> {}
            }


        }
    }
}