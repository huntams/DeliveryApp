package com.example.profiles

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.profiles.databinding.FragmentProfileBinding
import com.example.profiles.presentation.ProfileFeature
import com.example.profiles.presentation.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {


    private val binding by viewBinding(FragmentProfileBinding::bind)
    private val viewModel by viewModels<ProfileViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel.getOrders()
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.ordersLiveData.observe(viewLifecycleOwner) { orders ->
            binding.myComposeView.apply {
                setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
                setContent {
                    // In Compose world
                    DeliveryAppTheme {
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = Color.Black
                        ) {
                            /*
                            Orders(
                                orders = orders, modifier = Modifier
                                    .padding(8.dp)
                            )
                             */
                            ProfileFeature(
                            )
                        }
                    }
                }
            }
        }

        super.onViewCreated(view, savedInstanceState)
    }
}