package com.chrrissoft.room.navigation

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.chrrissoft.room.carriers.view.screens.CarriersScreen
import com.chrrissoft.room.carriers.view.viewmodels.CarriersViewModel
import com.chrrissoft.room.categories.view.screens.CategoriesScreen
import com.chrrissoft.room.categories.view.viewmodels.CategoriesViewModel
import com.chrrissoft.room.cities.view.screens.CitiesScreen
import com.chrrissoft.room.cities.view.viewmodels.CitiesViewModel
import com.chrrissoft.room.costumers.view.screens.CostumersScreen
import com.chrrissoft.room.costumers.view.viewmodels.CostumersViewModel
import com.chrrissoft.room.countries.view.screens.CountriesScreen
import com.chrrissoft.room.countries.view.viewmodels.CountriesViewModel
import com.chrrissoft.room.inventories.view.screens.InventoriesScreen
import com.chrrissoft.room.inventories.view.viewmodels.InventoriesViewModel
import com.chrrissoft.room.orders.view.screens.OrdersScreen
import com.chrrissoft.room.orders.view.viewmodels.OrdersViewModel
import com.chrrissoft.room.payments.view.screens.PaymentsScreen
import com.chrrissoft.room.payments.view.viewmodels.PaymentsViewModel
import com.chrrissoft.room.products.view.screens.ProductsScreen
import com.chrrissoft.room.products.view.viewmodels.ProductsViewModel
import com.chrrissoft.room.promotions.view.screens.PromotionsScreen
import com.chrrissoft.room.promotions.view.viewmodels.PromotionsViewModel
import com.chrrissoft.room.reviews.view.screens.ReviewsScreen
import com.chrrissoft.room.reviews.view.viewmodels.ReviewsViewModel
import com.chrrissoft.room.sales.view.screens.SalesScreen
import com.chrrissoft.room.sales.view.viewmodels.SalesViewModel
import com.chrrissoft.room.sellers.view.screens.SellersScreen
import com.chrrissoft.room.sellers.view.viewmodels.SellersViewModel
import com.chrrissoft.room.shipments.view.screens.ShipmentsScreen
import com.chrrissoft.room.shipments.view.viewmodels.ShipmentsViewModel
import com.chrrissoft.room.suppliers.view.screens.SuppliersScreen
import com.chrrissoft.room.suppliers.view.viewmodels.SuppliersViewModel
import com.chrrissoft.room.utils.ComposeUtils
import com.chrrissoft.room.utils.NavigationUtils.close
import com.chrrissoft.room.utils.NavigationUtils.open

@Composable
fun RoomGraph() {
    ComposeUtils.setBarsColors()
    val controller = rememberNavController()
    val currentStack by controller.currentBackStackEntryAsState()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerSheet(
                currentScreen = currentStack?.destination?.route,
                onNav = {
                    controller.navigate(it) { launchSingleTop = true }
                    drawerState.close(scope)
                },
            )
        }
    ) {
        NavHost(navController = controller, startDestination = Screen.Carriers.route) {
            screen(Screen.Carriers) {
                val viewModel = hiltViewModel<CarriersViewModel>()
                val state by viewModel.stateFlow.collectAsState()
                CarriersScreen(
                    state = state,
                    onEvent = { viewModel.handleEvent(it) },
                    onOpenDrawer = { drawerState.open(scope) }
                )
            }

            screen(Screen.Categories) {
                val viewModel = hiltViewModel<CategoriesViewModel>()
                val state by viewModel.stateFlow.collectAsState()
                CategoriesScreen(
                    state = state,
                    onEvent = { viewModel.handleEvent(it) },
                    onOpenDrawer = { drawerState.open(scope) }
                )
            }

            screen(Screen.Cities) {
                val viewModel = hiltViewModel<CitiesViewModel>()
                val state by viewModel.stateFlow.collectAsState()
                CitiesScreen(
                    state = state,
                    onEvent = { viewModel.handleEvent(it) },
                    onOpenDrawer = { drawerState.open(scope) }
                )
            }

            screen(Screen.Costumers) {
                val viewModel = hiltViewModel<CostumersViewModel>()
                val state by viewModel.stateFlow.collectAsState()
                CostumersScreen(
                    state = state,
                    onEvent = { viewModel.handleEvent(it) },
                    onOpenDrawer = { drawerState.open(scope) }
                )
            }

            screen(Screen.Countries) {
                val viewModel = hiltViewModel<CountriesViewModel>()
                val state by viewModel.stateFlow.collectAsState()
                CountriesScreen(
                    state = state,
                    onEvent = { viewModel.handleEvent(it) },
                    onOpenDrawer = { drawerState.open(scope) }
                )
            }

            screen(Screen.Inventories) {
                val viewModel = hiltViewModel<InventoriesViewModel>()
                val state by viewModel.stateFlow.collectAsState()
                InventoriesScreen(
                    state = state,
                    onEvent = { viewModel.handleEvent(it) },
                    onOpenDrawer = { drawerState.open(scope) }
                )
            }

            screen(Screen.Orders) {
                val viewModel = hiltViewModel<OrdersViewModel>()
                val state by viewModel.stateFlow.collectAsState()
                OrdersScreen(
                    state = state,
                    onEvent = { viewModel.handleEvent(it) },
                    onOpenDrawer = { drawerState.open(scope) }
                )
            }

            screen(Screen.Payments) {
                val viewModel = hiltViewModel<PaymentsViewModel>()
                val state by viewModel.stateFlow.collectAsState()
                PaymentsScreen(
                    state = state,
                    onEvent = { viewModel.handleEvent(it) },
                    onOpenDrawer = { drawerState.open(scope) }
                )
            }

            screen(Screen.Products) {
                val viewModel = hiltViewModel<ProductsViewModel>()
                val state by viewModel.stateFlow.collectAsState()
                ProductsScreen(
                    state = state,
                    onEvent = { viewModel.handleEvent(it) },
                    onOpenDrawer = { drawerState.open(scope) }
                )
            }

            screen(Screen.Promotions) {
                val viewModel = hiltViewModel<PromotionsViewModel>()
                val state by viewModel.stateFlow.collectAsState()
                PromotionsScreen(
                    state = state,
                    onEvent = { viewModel.handleEvent(it) },
                    onOpenDrawer = { drawerState.open(scope) }
                )
            }

            screen(Screen.Reviews) {
                val viewModel = hiltViewModel<ReviewsViewModel>()
                val state by viewModel.stateFlow.collectAsState()
                ReviewsScreen(
                    state = state,
                    onEvent = { viewModel.handleEvent(it) },
                    onOpenDrawer = { drawerState.open(scope) }
                )
            }

            screen(Screen.Sales) {
                val viewModel = hiltViewModel<SalesViewModel>()
                val state by viewModel.stateFlow.collectAsState()
                SalesScreen(
                    state = state,
                    onEvent = { viewModel.handleEvent(it) },
                    onOpenDrawer = { drawerState.open(scope) }
                )
            }

            screen(Screen.Sellers) {
                val viewModel = hiltViewModel<SellersViewModel>()
                val state by viewModel.stateFlow.collectAsState()
                SellersScreen(
                    state = state,
                    onEvent = { viewModel.handleEvent(it) },
                    onOpenDrawer = { drawerState.open(scope) }
                )
            }

            screen(Screen.Shipments) {
                val viewModel = hiltViewModel<ShipmentsViewModel>()
                val state by viewModel.stateFlow.collectAsState()
                ShipmentsScreen(
                    state = state,
                    onEvent = { viewModel.handleEvent(it) },
                    onOpenDrawer = { drawerState.open(scope) }
                )
            }

            screen(Screen.Suppliers) {
                val viewModel = hiltViewModel<SuppliersViewModel>()
                val state by viewModel.stateFlow.collectAsState()
                SuppliersScreen(
                    state = state,
                    onEvent = { viewModel.handleEvent(it) },
                    onOpenDrawer = { drawerState.open(scope) }
                )
            }
        }
    }
}
