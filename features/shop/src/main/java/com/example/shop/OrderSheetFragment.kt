package com.example.shop

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.shop.databinding.FragmentOrderBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderSheetFragment : BottomSheetDialogFragment(R.layout.fragment_order) {

    private val binding by viewBinding(FragmentOrderBinding::bind)

    private val viewModel by viewModels<ShopViewModel>()
    private val args: OrderSheetFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            viewModel.getOrderById(args.orderId)
            viewModel.orderLiveData.observe(viewLifecycleOwner) { order ->
                val total = "${viewModel.totalPrice(order)} р."
                setOneLineData(
                    oneLineViewProductPrice,
                    "${
                        order.productQuantity.sumOf {
                            it.productQuantity.quantity
                        }
                    } product",
                    total
                )
                setOneLineData(
                    oneLineViewDeliveryPrice, getString(R.string.delivery),
                    getString(R.string.free)
                )
                setOneLineData(
                    oneLineViewTotal,
                    getString(R.string.order_price), total
                )
            }
        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setOneLineData(v: OneLineTextView, description: String, data: String) {
        v.setDescription(description)
        v.setData(data)
    }
}