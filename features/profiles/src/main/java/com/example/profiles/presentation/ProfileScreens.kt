package com.example.profiles.presentation

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.profiles.R

enum class ProfileScreens(@StringRes val title: Int) {
    Start(title = R.string.hello),
    Orders(title = R.string.history_of_orders),
    Addresses(title = R.string.delivery_addresses),
    Settings(title = R.string.settings)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigateAppBar(
    @StringRes currentScreenTitle: Int,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        title = { Text(stringResource(currentScreenTitle)) },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.Clear,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarCustom(onSettingsButtonClicked: () -> Unit, scrollBehavior: TopAppBarScrollBehavior?) {
    TopAppBar(colors = TopAppBarDefaults.topAppBarColors(
        containerColor = MaterialTheme.colorScheme.primary
    ), title = {
        Column {
            Text(
                text = stringResource(R.string.hello),
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = stringResource(R.string.happy_day),
                style = MaterialTheme.typography.titleSmall
            )

        }

    }, actions = {
        IconButton(onClick = onSettingsButtonClicked) {
            Icon(
                imageVector = Icons.Filled.Info, contentDescription = "Localized description"
            )
        }
    }, scrollBehavior = scrollBehavior
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileFeature(viewModel: ProfileViewModel = viewModel()) {
    val navController = rememberNavController()
    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    // Get current back stack entry
    val suggestedDestinations by viewModel.ordersLiveData.collectAsStateWithLifecycle()
    val backStackEntry by navController.currentBackStackEntryAsState()
    // Get the name of the current screen
    val currentScreen = ProfileScreens.valueOf(
        backStackEntry?.destination?.route ?: ProfileScreens.Start.name
    )
    viewModel.getOrders()
    Scaffold(
        topBar = {
            if (navController.previousBackStackEntry != null) {

                NavigateAppBar(
                    currentScreenTitle = currentScreen.title,
                    canNavigateBack = navController.previousBackStackEntry != null,
                    navigateUp = { navController.navigateUp() }
                )
            } else {
                Column(modifier = Modifier.background(MaterialTheme.colorScheme.primary)) {
                    TopAppBarCustom(
                        { navController.navigate(ProfileScreens.Settings.name) },
                        scrollBehavior = scrollBehavior
                    )
                }
            }
        },
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { innerPadding ->
        //val uiState by viewModel.uiState.collectAsState()
        NavHost(
            navController = navController,
            startDestination = ProfileScreens.Start.name,
        ) {
            composable(route = ProfileScreens.Start.name) {
                ProfileScreen(
                    orders = suggestedDestinations,
                    onHistoryClicked = {
                        navController.navigate(ProfileScreens.Orders.name)
                    },
                    onAddressesClicked = {
                        navController.navigate(ProfileScreens.Addresses.name)
                    },
                    modifier = Modifier.padding(innerPadding)
                )
            }

            composable(route = ProfileScreens.Orders.name) {
                Orders(
                    orders = suggestedDestinations, modifier = Modifier.padding(innerPadding)
                )
            }

            composable(route = ProfileScreens.Addresses.name) {
                AddressScreen(
                    modifier = Modifier.padding(innerPadding)
                )
            }

            composable(route = ProfileScreens.Settings.name) {
                SettingsScreen(
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }
}