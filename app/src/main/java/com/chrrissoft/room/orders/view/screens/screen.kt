package com.chrrissoft.room.orders.view.screens

import androidx.compose.runtime.Composable
import com.chrrissoft.room.cities.view.states.CitiesState
import com.chrrissoft.room.orders.db.objects.Order
import com.chrrissoft.room.orders.db.objects.OrderWithNestedRelationship
import com.chrrissoft.room.orders.view.events.OrdersEvent
import com.chrrissoft.room.orders.view.states.OrdersState
import com.chrrissoft.room.orders.view.ui.OrderList
import com.chrrissoft.room.orders.view.ui.OrderWithRelationship
import com.chrrissoft.room.shipments.view.states.ShipmentsState
import com.chrrissoft.room.ui.components.AlarmManagerSnackbar
import com.chrrissoft.room.ui.components.CommonScreen
import com.chrrissoft.room.utils.ResStateUtils.getSuccess

@Composable
fun OrdersScreen(
    state: OrdersState,
    citiesState: CitiesState,
    shipmentsState: ShipmentsState,
    onEvent: (OrdersEvent) -> Unit,
    onOpenDrawer: () -> Unit,
) {
    CommonScreen(
        page = state.page,
        title = "Orders",
        onChangePage = { onEvent(OrdersEvent.OnChangePage(it)) },
        onSave = { state.detail.getSuccess()?.let { onEvent(OrdersEvent.OnSave(it)) } },
        onCreate = { onEvent(OrdersEvent.OnCreate(it to OrderWithNestedRelationship(Order(it)))) },
        onNavigation = onOpenDrawer,
        details = {
            OrderWithRelationship(
                state = state.detail,
                onStateChange = { onEvent(OrdersEvent.OnChange(it)) },
                cities = citiesState.listing,
                shipments = shipmentsState.listing,
            )
        },
        list = {
            OrderList(
                state = state.listing,
                onDelete = { onEvent(OrdersEvent.OnDelete(it)) },
                selected = setOf(state.detail.getSuccess()?.first),
                onSelect = { onEvent(OrdersEvent.OnOpen(it)) },
            )
        },
        snackbarHost = { AlarmManagerSnackbar(state = state.snackbar) }
    )
}
