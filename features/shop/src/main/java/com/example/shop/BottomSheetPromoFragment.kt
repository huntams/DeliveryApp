package com.example.shop

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.shop.databinding.FragmentPromocodeBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BottomSheetPromoFragment : BottomSheetDialogFragment(R.layout.fragment_promocode) {

    private val binding by viewBinding(FragmentPromocodeBinding::bind)

    private val args: BottomSheetPromoFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            buttonAccept.setOnClickListener {
                if (textInputEditTextPromo.text?.isEmpty() == true) {
                    textInputLayoutPromo.error = "Input promo-code"
                } else {
                    val builder = MaterialAlertDialogBuilder(requireContext())
                    builder.setMessage("YOU WIN!!!")
                    builder.setTitle("You received ${args.randomCoins} coins!!")
                    builder.setPositiveButton(
                        "Accept"
                    ) { _, _ ->
                    }
                    builder.create().show()

                    findNavController().popBackStack()
                }
            }

        }
        super.onViewCreated(view, savedInstanceState)
    }

}