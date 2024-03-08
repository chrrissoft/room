package com.chrrissoft.room.cities.view.screens

import androidx.compose.runtime.Composable
import com.chrrissoft.room.carriers.view.states.CarriersState
import com.chrrissoft.room.cities.db.objects.City
import com.chrrissoft.room.cities.db.objects.CityWithNestedRelationship
import com.chrrissoft.room.cities.view.events.CitiesEvent
import com.chrrissoft.room.cities.view.states.CitiesState
import com.chrrissoft.room.cities.view.ui.CitiesList
import com.chrrissoft.room.cities.view.ui.CityWithRelationship
import com.chrrissoft.room.costumers.view.states.CostumersState
import com.chrrissoft.room.countries.view.states.CountriesState
import com.chrrissoft.room.orders.view.states.OrdersState
import com.chrrissoft.room.sellers.view.states.SellersState
import com.chrrissoft.room.shipments.view.states.ShipmentsState
import com.chrrissoft.room.suppliers.view.states.SuppliersState
import com.chrrissoft.room.ui.components.AlarmManagerSnackbar
import com.chrrissoft.room.ui.components.CommonScreen
import com.chrrissoft.room.utils.ResStateUtils.getSuccess

@Composable
fun CitiesScreen(
    state: CitiesState,
    carriersState: CarriersState,
    costumersState: CostumersState,
    ordersState: OrdersState,
    sellersState: SellersState,
    shipmentsState: ShipmentsState,
    suppliersState: SuppliersState,
    onEvent: (CitiesEvent) -> Unit,
    countriesState: CountriesState,
    onOpenDrawer: () -> Unit,
) {
    CommonScreen(
        page = state.page,
        title = "Cities",
        onChangePage = { onEvent(CitiesEvent.OnChangePage(it)) },
        onSave = { state.detail.getSuccess()?.let { onEvent(CitiesEvent.OnSave(it)) } },
        onCreate = { onEvent(CitiesEvent.OnCreate(it to CityWithNestedRelationship(City(it)))) },
        onNavigation = onOpenDrawer,
        details = {
            CityWithRelationship(
                state = state.detail,
                onStateChange = { onEvent(CitiesEvent.OnChange(it)) },
                countries = countriesState.listing,
                carriers = carriersState.listing,
                onRemoveCarriers = {},
                onAddCarriers = {},
                costumers = costumersState.listing,
                onRemoveCostumers = {},
                onAddCostumers = {},
                orders = ordersState.listing,
                onRemoveOrders = {},
                onAddOrders = {},
                sellers = sellersState.listing,
                onRemoveSellers = {},
                onAddSellers = {},
                shipments = shipmentsState.listing,
                onRemoveShipments = {},
                onAddShipments = {},
                suppliers = suppliersState.listing,
                onRemoveSuppliers = {},
                onAddSuppliers = {},
            )
        },
        list = {
            CitiesList(
                state = state.listing,
                onDelete = { onEvent(CitiesEvent.OnDelete(it)) },
                selected = setOf(state.detail.getSuccess()?.first),
                onSelect = { onEvent(CitiesEvent.OnOpen(it)) },
            )
        },
        snackbarHost = { AlarmManagerSnackbar(state = state.snackbar) }
    )
}
