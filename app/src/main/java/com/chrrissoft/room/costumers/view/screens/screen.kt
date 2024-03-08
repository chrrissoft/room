package com.chrrissoft.room.costumers.view.screens

import androidx.compose.runtime.Composable
import com.chrrissoft.room.cities.view.states.CitiesState
import com.chrrissoft.room.costumers.db.objects.Costumer
import com.chrrissoft.room.costumers.db.objects.CostumerWithNestedRelationship
import com.chrrissoft.room.costumers.view.events.CostumersEvent
import com.chrrissoft.room.costumers.view.events.CostumersEvent.*
import com.chrrissoft.room.costumers.view.states.CostumersState
import com.chrrissoft.room.costumers.view.ui.CostumerWithRelationship
import com.chrrissoft.room.costumers.view.ui.CostumersList
import com.chrrissoft.room.products.view.states.ProductsState
import com.chrrissoft.room.sales.view.states.SalesState
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shipments.view.states.ShipmentsState
import com.chrrissoft.room.ui.components.AlarmManagerSnackbar
import com.chrrissoft.room.ui.components.CommonScreen
import com.chrrissoft.room.utils.ResStateUtils.getSuccess

@Composable
fun CostumersScreen(
    state: CostumersState,
    citiesState: CitiesState,
    salesState: SalesState,
    productsState: ProductsState,
    shipmentsState: ShipmentsState,
    onEvent: (CostumersEvent) -> Unit,
    onOpenDrawer: () -> Unit,
) {
    CommonScreen(
        page = state.page,
        title = "Costumers",
        onChangePage = { onEvent(OnChangePage(it)) },
        onSave = { state.detail.getSuccess()?.let { onEvent(OnSave(it)) } },
        onCreate = { onEvent(OnCreate(it to CostumerWithNestedRelationship(Costumer(it)))) },
        onNavigation = onOpenDrawer,
        details = {
            CostumerWithRelationship(
                state = state.detail,
                onStateChange = { onEvent(OnChange(it)) },
                sales = salesState.listing,
                onRemoveSales = {},
                onAddSales = {},
                products = productsState.listing,
                onRemoveProducts = {},
                onAddProducts = {},
                shipments = shipmentsState.listing,
                onRemoveShipments = {},
                onAddShipments = {},
                cities = citiesState.listing,
            )
        },
        list = {
            CostumersList(
                state = state.listing,
                onDelete = { onEvent(OnDelete(it)) },
                selected = setOf(state.detail.getSuccess()?.first),
                onSelect = { onEvent(OnOpen(it)) },
            )
        },
        snackbarHost = { AlarmManagerSnackbar(state = state.snackbar) }
    )
}
