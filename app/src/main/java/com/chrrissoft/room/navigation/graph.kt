package com.chrrissoft.room.navigation

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
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
import com.chrrissoft.room.cross.view.viewmodel.CrossRefViewModel
import com.chrrissoft.room.orders.view.screens.OrdersScreen
import com.chrrissoft.room.orders.view.viewmodels.OrdersViewModel
import com.chrrissoft.room.products.view.screens.ProductsScreen
import com.chrrissoft.room.products.view.viewmodels.ProductsViewModel
import com.chrrissoft.room.promotions.view.screens.PromotionsScreen
import com.chrrissoft.room.promotions.view.viewmodels.PromotionsViewModel
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
        val crossViewModel = viewModel<CrossRefViewModel>()
        val crossState by crossViewModel.stateFlow.collectAsState()

        NavHost(navController = controller, startDestination = Screen.Carriers.route) {
            screen(Screen.Carriers) {
                val viewModel = hiltViewModel<CarriersViewModel>()
                val state by viewModel.stateFlow.collectAsState()
                val citiesViewModel = hiltViewModel<CitiesViewModel>()
                val citiesState by citiesViewModel.stateFlow.collectAsState()
                val shipmentsViewModel = hiltViewModel<ShipmentsViewModel>()
                val shipmentsState by shipmentsViewModel.stateFlow.collectAsState()
                CarriersScreen(
                    state = state,
                    citiesState = citiesState,
                    shipmentsState = shipmentsState,
                    onEvent = { viewModel.handleEvent(it) },
                    onShipmentsEvent = { shipmentsViewModel.handleEvent(it) },
                    onOpenDrawer = { drawerState.open(scope) },
                )
            }

            screen(Screen.Categories) {
                val viewModel = hiltViewModel<CategoriesViewModel>()
                val state by viewModel.stateFlow.collectAsState()
                val promotionsViewModel = hiltViewModel<PromotionsViewModel>()
                val promotionsState by promotionsViewModel.stateFlow.collectAsState()
                val salesViewModel = hiltViewModel<SalesViewModel>()
                val salesState by salesViewModel.stateFlow.collectAsState()
                val suppliersViewModel = hiltViewModel<SuppliersViewModel>()
                val suppliersState by suppliersViewModel.stateFlow.collectAsState()
                CategoriesScreen(
                    state = state,
                    promotionsState = promotionsState,
                    salesState = salesState,
                    suppliersState = suppliersState,
                    crossState = crossState,
                    onEvent = { viewModel.handleEvent(it) },
                    onCrossEvent = { crossViewModel.handleEvent(it) },
                    onOpenDrawer = { drawerState.open(scope) }
                )
            }

            screen(Screen.Cities) {
                val viewModel = hiltViewModel<CitiesViewModel>()
                val state by viewModel.stateFlow.collectAsState()
                val countriesViewModel = hiltViewModel<CountriesViewModel>()
                val countriesState by countriesViewModel.stateFlow.collectAsState()
                val carriersViewModel = hiltViewModel<CarriersViewModel>()
                val carriersState by carriersViewModel.stateFlow.collectAsState()
                val ordersViewModel = hiltViewModel<OrdersViewModel>()
                val ordersState by ordersViewModel.stateFlow.collectAsState()
                val costumersViewModel = hiltViewModel<CostumersViewModel>()
                val costumersState by costumersViewModel.stateFlow.collectAsState()
                val sellersViewModel = hiltViewModel<SellersViewModel>()
                val sellersState by sellersViewModel.stateFlow.collectAsState()
                val shipmentsViewModel = hiltViewModel<ShipmentsViewModel>()
                val shipmentsState by shipmentsViewModel.stateFlow.collectAsState()
                val suppliersViewModel = hiltViewModel<SuppliersViewModel>()
                val suppliersState by suppliersViewModel.stateFlow.collectAsState()
                CitiesScreen(
                    state = state,
                    countriesState = countriesState,
                    carriersState = carriersState,
                    costumersState = costumersState,
                    ordersState = ordersState,
                    sellersState = sellersState,
                    shipmentsState = shipmentsState,
                    suppliersState = suppliersState,
                    crossState = crossState,
                    onEvent = { viewModel.handleEvent(it) },
                    onCrossEvent = { crossViewModel.handleEvent(it) },
                    onCarriersEvent = { carriersViewModel.handleEvent(it) },
                    onCostumersEvent = { costumersViewModel.handleEvent(it) },
                    onOrdersEvent = { ordersViewModel.handleEvent(it) },
                    onSellersEvent = { sellersViewModel.handleEvent(it) },
                    onOpenDrawer = { drawerState.open(scope) },
                )
            }

            screen(Screen.Costumers) {
                val viewModel = hiltViewModel<CostumersViewModel>()
                val state by viewModel.stateFlow.collectAsState()
                val citiesViewModel = hiltViewModel<CitiesViewModel>()
                val citiesState by citiesViewModel.stateFlow.collectAsState()
                val salesViewModel = hiltViewModel<SalesViewModel>()
                val salesState by salesViewModel.stateFlow.collectAsState()
                val productsViewModel = hiltViewModel<ProductsViewModel>()
                val productsState by productsViewModel.stateFlow.collectAsState()
                val shipmentsViewModel = hiltViewModel<ShipmentsViewModel>()
                val shipmentsState by shipmentsViewModel.stateFlow.collectAsState()
                CostumersScreen(
                    state = state,
                    citiesState = citiesState,
                    salesState = salesState,
                    productsState = productsState,
                    shipmentsState = shipmentsState,
                    crossState = crossState,
                    onEvent = { viewModel.handleEvent(it) },
                    onSalesEvent = { salesViewModel.handleEvent(it) },
                    onCrossEvent = { crossViewModel.handleEvent(it) },
                    onOpenDrawer = { drawerState.open(scope) }
                )
            }

            screen(Screen.Countries) {
                val viewModel = hiltViewModel<CountriesViewModel>()
                val state by viewModel.stateFlow.collectAsState()
                val citiesViewModel = hiltViewModel<CitiesViewModel>()
                val citiesState by citiesViewModel.stateFlow.collectAsState()
                CountriesScreen(
                    state = state,
                    citiesState = citiesState,
                    crossState = crossState,
                    onEvent = { viewModel.handleEvent(it) },
                    onCitiesEvent = { citiesViewModel.handleEvent(it) },
                    onOpenDrawer = { drawerState.open(scope) },
                )
            }

            screen(Screen.Orders) {
                val viewModel = hiltViewModel<OrdersViewModel>()
                val state by viewModel.stateFlow.collectAsState()
                val salesViewModel = hiltViewModel<SalesViewModel>()
                val salesState by salesViewModel.stateFlow.collectAsState()
                val citiesViewModel = hiltViewModel<CitiesViewModel>()
                val citiesState by citiesViewModel.stateFlow.collectAsState()
                val shipmentsViewModel = hiltViewModel<ShipmentsViewModel>()
                val shipmentsState by shipmentsViewModel.stateFlow.collectAsState()
                OrdersScreen(
                    state = state,
                    salesState = salesState,
                    citiesState = citiesState,
                    shipmentsState = shipmentsState,
                    crossState = crossState,
                    onEvent = { viewModel.handleEvent(it) },
                    onOpenDrawer = { drawerState.open(scope) }
                )
            }

            screen(Screen.Products) {
                val viewModel = hiltViewModel<ProductsViewModel>()
                val state by viewModel.stateFlow.collectAsState()
                val promotionsViewModel = hiltViewModel<PromotionsViewModel>()
                val promotionsState by promotionsViewModel.stateFlow.collectAsState()
                val costumersViewModel = hiltViewModel<CostumersViewModel>()
                val costumersState by costumersViewModel.stateFlow.collectAsState()
                val sellersViewModel = hiltViewModel<SellersViewModel>()
                val sellersState by sellersViewModel.stateFlow.collectAsState()
                val suppliersViewModel = hiltViewModel<SuppliersViewModel>()
                val suppliersState by suppliersViewModel.stateFlow.collectAsState()
                val salesViewModel = hiltViewModel<SalesViewModel>()
                val salesState by salesViewModel.stateFlow.collectAsState()

                ProductsScreen(
                    state = state,
                    promotionsState = promotionsState,
                    costumersState = costumersState,
                    salesState = salesState,
                    suppliersState = suppliersState,
                    sellersState = sellersState,
                    crossState = crossState,
                    onEvent = { viewModel.handleEvent(it) },
                    onCrossEvent = { crossViewModel.handleEvent(it) },
                    onOpenDrawer = { drawerState.open(scope) },
                )
            }

            screen(Screen.Promotions) {
                val viewModel = hiltViewModel<PromotionsViewModel>()
                val state by viewModel.stateFlow.collectAsState()
                val salesViewModel = hiltViewModel<SalesViewModel>()
                val salesState by salesViewModel.stateFlow.collectAsState()
                val productsViewModel = hiltViewModel<ProductsViewModel>()
                val productsState by productsViewModel.stateFlow.collectAsState()
                val categoriesViewModel = hiltViewModel<CategoriesViewModel>()
                val categoriesState by categoriesViewModel.stateFlow.collectAsState()
                PromotionsScreen(
                    state = state,
                    salesState = salesState,
                    productsState = productsState,
                    categoriesState = categoriesState,
                    crossState = crossState,
                    onEvent = { viewModel.handleEvent(it) },
                    onCrossEvent = { crossViewModel.handleEvent(it) },
                    onProductsEvent = { productsViewModel.handleEvent(it) },
                    onOpenDrawer = { drawerState.open(scope) }
                )
            }

            screen(Screen.Sales) {
                val viewModel = hiltViewModel<SalesViewModel>()
                val state by viewModel.stateFlow.collectAsState()
                val costumersViewModel = hiltViewModel<CostumersViewModel>()
                val costumersState by costumersViewModel.stateFlow.collectAsState()
                val sellersViewModel = hiltViewModel<SellersViewModel>()
                val sellersState by sellersViewModel.stateFlow.collectAsState()
                val ordersViewModel = hiltViewModel<OrdersViewModel>()
                val ordersState by ordersViewModel.stateFlow.collectAsState()
                val suppliersViewModel = hiltViewModel<SuppliersViewModel>()
                val suppliersState by suppliersViewModel.stateFlow.collectAsState()
                val categoriesViewModel = hiltViewModel<CategoriesViewModel>()
                val categoriesState by categoriesViewModel.stateFlow.collectAsState()
                val productsViewModel = hiltViewModel<ProductsViewModel>()
                val productsState by productsViewModel.stateFlow.collectAsState()
                val promotionsViewModel = hiltViewModel<PromotionsViewModel>()
                val promotionsState by promotionsViewModel.stateFlow.collectAsState()
                SalesScreen(
                    state = state,
                    costumersState = costumersState,
                    sellersState = sellersState,
                    ordersState = ordersState,
                    suppliersState = suppliersState,
                    categoriesState = categoriesState,
                    productsState = productsState,
                    promotionsState = promotionsState,
                    crossState = crossState,
                    onEvent = { viewModel.handleEvent(it) },
                    onCrossEvent = { crossViewModel.handleEvent(it) },
                    onOpenDrawer = { drawerState.open(scope) }
                )
            }

            screen(Screen.Sellers) {
                val viewModel = hiltViewModel<SellersViewModel>()
                val state by viewModel.stateFlow.collectAsState()
                val citiesViewModel = hiltViewModel<CitiesViewModel>()
                val citiesState by citiesViewModel.stateFlow.collectAsState()
                val salesViewModel = hiltViewModel<SalesViewModel>()
                val salesState by salesViewModel.stateFlow.collectAsState()
                val productsViewModel = hiltViewModel<ProductsViewModel>()
                val productsState by productsViewModel.stateFlow.collectAsState()
                val shipmentsViewModel = hiltViewModel<ShipmentsViewModel>()
                val shipmentsState by shipmentsViewModel.stateFlow.collectAsState()
                SellersScreen(
                    state = state,
                    citiesState = citiesState,
                    salesState = salesState,
                    productsState = productsState,
                    shipmentsState = shipmentsState,
                    crossState = crossState,
                    onEvent = { viewModel.handleEvent(it) },
                    onSalesEvent = { salesViewModel.handleEvent(it) },
                    onCrossEvent = { crossViewModel.handleEvent(it) },
                    onOpenDrawer = { drawerState.open(scope) },
                )
            }

            screen(Screen.Shipments) {
                val viewModel = hiltViewModel<ShipmentsViewModel>()
                val state by viewModel.stateFlow.collectAsState()
                val carriersViewModel = hiltViewModel<CarriersViewModel>()
                val carriersState by carriersViewModel.stateFlow.collectAsState()
                val ordersViewModel = hiltViewModel<OrdersViewModel>()
                val ordersState by ordersViewModel.stateFlow.collectAsState()
                val citiesViewModel = hiltViewModel<CitiesViewModel>()
                val citiesState by citiesViewModel.stateFlow.collectAsState()
                val costumersViewModel = hiltViewModel<CostumersViewModel>()
                val costumersState by costumersViewModel.stateFlow.collectAsState()
                val sellersViewModel = hiltViewModel<SellersViewModel>()
                val sellersState by sellersViewModel.stateFlow.collectAsState()
                ShipmentsScreen(
                    state = state,
                    carriersState = carriersState,
                    ordersState = ordersState,
                    citiesState = citiesState,
                    costumersState = costumersState,
                    sellersState = sellersState,
                    crossState = crossState,
                    onEvent = { viewModel.handleEvent(it) },
                    onCrossEvent = { crossViewModel.handleEvent(it) },
                    onOrdersEvent = { ordersViewModel.handleEvent(it) },
                    onOpenDrawer = { drawerState.open(scope) }
                )
            }

            screen(Screen.Suppliers) {
                val viewModel = hiltViewModel<SuppliersViewModel>()
                val state by viewModel.stateFlow.collectAsState()
                val citiesViewModel = hiltViewModel<CitiesViewModel>()
                val citiesState by citiesViewModel.stateFlow.collectAsState()
                val productsViewModel = hiltViewModel<ProductsViewModel>()
                val productsState by productsViewModel.stateFlow.collectAsState()
                val salesViewModel = hiltViewModel<SalesViewModel>()
                val salesState by salesViewModel.stateFlow.collectAsState()
                val categoriesViewModel = hiltViewModel<CategoriesViewModel>()
                val categoriesState by categoriesViewModel.stateFlow.collectAsState()
                SuppliersScreen(
                    state = state,
                    salesState = salesState,
                    citiesState = citiesState,
                    productsState = productsState,
                    categoriesState = categoriesState,
                    crossState = crossState,
                    onEvent = { viewModel.handleEvent(it) },
                    onCrossEvent = { crossViewModel.handleEvent(it) },
                    onOpenDrawer = { drawerState.open(scope) }
                )
            }
        }
    }
}
