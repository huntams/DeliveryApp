package com.example.shop

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.common.PrefsStorage
import com.example.common.getFormattedPrice
import com.example.shop.databinding.FragmentOrderBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OrderSheetFragment : BottomSheetDialogFragment(R.layout.fragment_order) {

    private val binding by viewBinding(FragmentOrderBinding::bind)

    @Inject
    lateinit var prefsStorage: PrefsStorage
    private val viewModel by viewModels<ShopViewModel>()
    private val args: OrderSheetFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            viewModel.getOrderById(args.orderId)
            viewModel.orderLiveData.observe(viewLifecycleOwner) { order ->
                val total = getFormattedPrice(viewModel.totalPrice(order) + args.deliveryPrice)
                setOneLineData(
                    oneLineViewProductPrice,
                    "${
                        order.productQuantity.sumOf {
                            it.productQuantity.quantity
                        }
                    } product",
                    getFormattedPrice(viewModel.totalPrice(order))
                )
                setOneLineData(
                    oneLineViewDeliveryPrice, getString(R.string.delivery),
                    getFormattedPrice(args.deliveryPrice)
                )
                setOneLineData(
                    oneLineViewTotal,
                    getString(R.string.order_price), total
                )
                buttonOrder.setOnClickListener {
                    viewModel.addOrderById(
                        orderId = order.order.orderId,
                        totalPrice = viewModel.totalPrice(order) + args.deliveryPrice,
                        coins = args.deliveryCoins
                    )
                    viewModel.addOrder()
                    viewModel.idLiveData.observe(viewLifecycleOwner) {
                        prefsStorage.order = it
                        findNavController().navigate(OrderSheetFragmentDirections.actionOrderSheetFragmentToShopFragment())
                    }
                }
            }
        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setOneLineData(v: OneLineTextView, description: String, data: String) {
        v.setDescription(description)
        v.setData(data)
    }
}