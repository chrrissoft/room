package com.chrrissoft.room.shipments.view.screens

import androidx.compose.runtime.Composable
import com.chrrissoft.room.carriers.view.states.CarriersState
import com.chrrissoft.room.orders.view.states.OrdersState
import com.chrrissoft.room.shipments.db.objects.Shipping
import com.chrrissoft.room.shipments.db.objects.ShippingWithRelationship
import com.chrrissoft.room.shipments.view.events.ShipmentsEvent
import com.chrrissoft.room.shipments.view.states.ShipmentsState
import com.chrrissoft.room.shipments.view.ui.ShipmentsList
import com.chrrissoft.room.shipments.view.ui.ShippingWithRelationship
import com.chrrissoft.room.ui.components.AlarmManagerSnackbar
import com.chrrissoft.room.ui.components.CommonScreen
import com.chrrissoft.room.utils.ResStateUtils.getSuccess

@Composable
fun ShipmentsScreen(
    state: ShipmentsState,
    carriersState: CarriersState,
    ordersState: OrdersState,
    onEvent: (ShipmentsEvent) -> Unit,
    onOpenDrawer: () -> Unit,
) {
    CommonScreen(
        page = state.page,
        title = "Shipments",
        onChangePage = { onEvent(ShipmentsEvent.OnChangePage(it)) },
        onSave = { state.detail.getSuccess()?.let { onEvent(ShipmentsEvent.OnSave(it)) } },
        onCreate = { onEvent(ShipmentsEvent.OnCreate(it to ShippingWithRelationship(Shipping(it)))) },
        onNavigation = onOpenDrawer,
        details = {
            ShippingWithRelationship(
                state = state.detail,
                onStateChange = { onEvent(ShipmentsEvent.OnChange(it)) },
                carriers = carriersState.listing,
                orders = ordersState.listing,
            )
        },
        list = {
            ShipmentsList(
                state = state.listing,
                onDelete = { onEvent(ShipmentsEvent.OnDelete(it)) },
                selected = setOf(state.detail.getSuccess()?.first),
                onSelect = { onEvent(ShipmentsEvent.OnOpen(it)) },
            )
        },
        snackbarHost = { AlarmManagerSnackbar(state = state.snackbar) }
    )
}
