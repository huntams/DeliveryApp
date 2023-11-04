package com.example.deliveryapp.presentation.Products

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.deliveryapp.R
import com.example.deliveryapp.databinding.FragmentProductsBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProductsFragment : Fragment(R.layout.fragment_products) {


    private val binding by viewBinding(FragmentProductsBinding::bind)
    private val viewModel by viewModels<ProductsViewModel>()

    @Inject
    lateinit var categoriesAdapter: CategoriesAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getCategories()
        viewModel.categoriesLiveData.observe(viewLifecycleOwner){
            categoriesAdapter.submitList(it.meals)
            binding.recyclerView.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = categoriesAdapter
            }
            binding.toolbar.title = it.meals[0].strCategory
        }

    }
}