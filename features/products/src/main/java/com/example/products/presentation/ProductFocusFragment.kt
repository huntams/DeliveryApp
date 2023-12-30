package com.example.products.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.products.R
import com.example.products.databinding.FragmentFocusProductBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductFocusFragment : Fragment(R.layout.fragment_focus_product) {

    private val binding by viewBinding(FragmentFocusProductBinding::bind)
    private val viewModel by viewModels<ProductsViewModel>()

    private val args: ProductFocusFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        with(binding){
            textViewInfoProduct.text = args.productId.toString()
        }
    }
}