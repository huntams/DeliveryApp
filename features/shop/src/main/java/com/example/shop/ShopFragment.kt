package com.example.shop

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.common.PrefsStorage
import com.example.common.cameraPermissionRequest
import com.example.common.getFormattedPrice
import com.example.common.isPermissionGranted
import com.example.common.openPermissionSetting
import com.example.shop.databinding.FragmentShopBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.random.Random

@AndroidEntryPoint
class ShopFragment : Fragment(R.layout.fragment_shop) {
    private val binding by viewBinding(FragmentShopBinding::bind)

    private val viewModel by viewModels<ShopViewModel>()

    @Inject
    lateinit var prefs: PrefsStorage

    @Inject
    lateinit var orderAdapter: OrderAdapter

    @Inject
    lateinit var addProductAdapter: AddProductAdapter


    private val cameraPermission = android.Manifest.permission.CAMERA

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted)
                findNavController().navigate(com.example.qrcode.R.id.qrcode_nav_graph)
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var total = 0
        val randomCoins = Random.nextInt(10, 200)
        val deliveryPrice: Int = Random.nextInt(300, 1000)
        viewModel.addOrderById(prefs.order, 0, 0)
        viewModel.getOrderById(prefs.order)
        viewModel.orderLiveData.observe(viewLifecycleOwner) { order ->

            orderAdapter.submitList(order.productQuantity)
            total = viewModel.totalPrice(order)
            val items = order.productQuantity.sumOf {
                it.productQuantity.quantity
            }
            with(binding) {
                recyclerViewProducts.adapter = orderAdapter
                textViewDeliveryPrice.text = getString(R.string.delivery_price,getFormattedPrice(deliveryPrice))
                textViewTotalPrice.text = getString(R.string.total_price,items,getFormattedPrice(total))
                buttonOrder.text = getString(R.string.button_order,getFormattedPrice(total + deliveryPrice))
                oneLineViewCoins.setDescription(getString(R.string.delivery_coins))
                oneLineViewCoins.setData("${(total / 20)} d.")

                binding.buttonOrder.setOnClickListener {
                    if (items != 0) {
                        findNavController().navigate(
                            ShopFragmentDirections.actionShopFragmentToOrderSheetFragment(
                                order.order.orderId,
                                (total / 20) + randomCoins,
                                deliveryPrice
                            )
                        )
                        viewModel.getOrderById(prefs.order)
                    }
                }
            }
        }
        viewModel.getProducts()
        viewModel.productsLiveData.observe(viewLifecycleOwner) { products ->
            addProductAdapter.submitList(products)
            binding.recyclerViewAddProduct.adapter = addProductAdapter
            addProductAdapter.setButtonCallback { apiproduct ->

                viewModel.addProductCrossRef(
                    productId = apiproduct.id,
                    productQuantityId = apiproduct.id,
                )
                viewModel.addProductQuantity(
                    quantity = 1,
                    orderId = prefs.order,
                    productId = apiproduct.id
                )
            }
        }
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.QRCode -> {
                    requestCameraAndStart()
                    true
                }

                else -> {
                    true
                }
            }
        }
        binding.buttonLocation.setOnClickListener {
            Toast.makeText(requireContext(), "Need api key for google maps", Toast.LENGTH_LONG)
                .show()
        }
        orderAdapter.setCallbackMinusButton {
            if (it.productQuantity.quantity - 1 > 0)
                viewModel.addProductQuantity(
                    orderId = it.productQuantity.orderId,
                    quantity = it.productQuantity.quantity - 1,
                    productId = it.productQuantity.id
                )
            else
                viewModel.deleteProductQuantity(it.productQuantity)
        }
        orderAdapter.setCallbackPlusButton {
            viewModel.addProductQuantity(
                orderId = it.productQuantity.orderId,
                quantity = it.productQuantity.quantity + 1,
                productId = it.productQuantity.id
            )
        }
        binding.buttonPromo.setOnClickListener {
            findNavController().navigate(
                ShopFragmentDirections.actionShopFragmentToBottomSheetPromoFragment(
                    randomCoins
                )
            )
            binding.oneLineViewCoins.setData("${(total / 20) + randomCoins} d.")
        }


    }

    private fun requestCameraAndStart() {
        if (requireContext().isPermissionGranted(cameraPermission)) {
            findNavController().navigate(com.example.qrcode.R.id.qrcode_nav_graph)
        } else
            requestCameraPermission()
    }

    private fun requestCameraPermission() {
        when {
            shouldShowRequestPermissionRationale(cameraPermission) -> {
                requireContext().cameraPermissionRequest {
                    requireContext().openPermissionSetting()
                }
            }

            else -> {
                requestPermissionLauncher.launch(cameraPermission)
            }
        }
    }
}