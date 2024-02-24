package com.chrrissoft.room.shipments.view.screens

import androidx.compose.runtime.Composable
import com.chrrissoft.room.shared.app.ResState
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
    onEvent: (ShipmentsEvent) -> Unit,
    onOpenDrawer: () -> Unit,
) {
    CommonScreen(
        page = state.page,
        title = "Shipments",
        onChangePage = { onEvent(ShipmentsEvent.OnChangePage(it)) },
        onSave = { state.shipping.getSuccess()?.let { onEvent(ShipmentsEvent.OnSave(it)) } },
        onCreate = { onEvent(ShipmentsEvent.OnCreate(it to ShippingWithRelationship(Shipping(it)))) },
        onNavigation = onOpenDrawer,
        details = {
            ShippingWithRelationship(
                state = state.shipping,
                onStateChange = { onEvent(ShipmentsEvent.OnChange(it)) },
                orders = ResState.None,
                sales = emptyList(),
                carriers = emptyList(),
            )
        },
        list = {
            ShipmentsList(
                state = state.shipments,
                onDelete = { onEvent(ShipmentsEvent.OnDelete(it)) },
                selected = setOf(),
                onSelect = { onEvent(ShipmentsEvent.OnOpen(it.first)) },
            )
        },
        snackbarHost = { AlarmManagerSnackbar(state = state.snackbar) }
    )
}
