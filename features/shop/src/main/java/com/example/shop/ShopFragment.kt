package com.example.shop

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.common.PrefsStorage
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
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var total: Int
        viewModel.getOrderById(prefs.order)
        viewModel.orderLiveData.observe(viewLifecycleOwner) { order ->
            orderAdapter.submitList(order.productQuantity)
            total = viewModel.totalPrice(order)
            val items = order.productQuantity.sumOf {
                it.productQuantity.quantity
            }
            with(binding) {
                recyclerViewProducts.adapter = orderAdapter
                val priceText = "${getString(R.string.delivery_price)} ${viewModel.price} р."
                textViewDeliveryPrice.text = priceText
                val totalText = "$items ${getString(R.string.total_price)} $total р."
                textViewTotalPrice.text = totalText
                buttonOrder.text = "${getString(R.string.button_order)} $total р."
                oneLineViewCoins.setDescription(getString(R.string.delivery_coins))
                oneLineViewCoins.setData("${(total / 20)} р.")
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
                val randomCoins = Random.nextInt(10, 200)
                findNavController().navigate(
                    ShopFragmentDirections.actionShopFragmentToBottomSheetPromoFragment(
                        randomCoins
                    )
                )
                binding.oneLineViewCoins.setData("${(total / 20) + randomCoins} р.")
            }
            binding.buttonOrder.setOnClickListener {
                findNavController().navigate(
                    ShopFragmentDirections.actionShopFragmentToOrderSheetFragment(
                        order.order.orderId
                    )
                )
            }
        }

    }
}