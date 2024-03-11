package com.chrrissoft.room.carriers.view.screens

import androidx.compose.runtime.Composable
import com.chrrissoft.room.carriers.db.objects.Carrier
import com.chrrissoft.room.carriers.db.objects.CarrierWithNestedRelationship
import com.chrrissoft.room.carriers.view.events.CarriersEvent
import com.chrrissoft.room.carriers.view.events.CarriersEvent.OnChange
import com.chrrissoft.room.carriers.view.events.CarriersEvent.OnChangePage
import com.chrrissoft.room.carriers.view.events.CarriersEvent.OnCreate
import com.chrrissoft.room.carriers.view.events.CarriersEvent.OnDelete
import com.chrrissoft.room.carriers.view.events.CarriersEvent.OnOpen
import com.chrrissoft.room.carriers.view.events.CarriersEvent.OnSave
import com.chrrissoft.room.carriers.view.states.CarriersState
import com.chrrissoft.room.carriers.view.ui.CarrierWithRelationship
import com.chrrissoft.room.carriers.view.ui.CarriersList
import com.chrrissoft.room.cities.view.states.CitiesState
import com.chrrissoft.room.shipments.view.events.ShipmentsEvent
import com.chrrissoft.room.shipments.view.events.ShipmentsEvent.OnSaveRaw
import com.chrrissoft.room.shipments.view.states.ShipmentsState
import com.chrrissoft.room.ui.components.AlarmManagerSnackbar
import com.chrrissoft.room.ui.components.CommonScreen
import com.chrrissoft.room.utils.ResStateUtils.getSuccess

@Composable
fun CarriersScreen(
    state: CarriersState,
    citiesState: CitiesState,
    shipmentsState: ShipmentsState,
    onEvent: (CarriersEvent) -> Unit,
    onShipmentsEvent: (ShipmentsEvent) -> Unit,
    onOpenDrawer: () -> Unit,
) {
    CommonScreen(
        page = state.page,
        title = "Carriers",
        onChangePage = { onEvent(OnChangePage(it)) },
        onSave = { state.detail.getSuccess()?.let { onEvent(OnSave(it)) } },
        onCreate = { onEvent(OnCreate(it to CarrierWithNestedRelationship(Carrier(it)))) },
        onNavigation = onOpenDrawer,
        details = {
            CarrierWithRelationship(
                state = state.detail,
                onStateChange = { onEvent(OnChange(it)) },
                shipments = shipmentsState.listing,
                onRemoveShipments = { data ->
                    data.mapValues { it.value.shipping.copy(carrierId = null) }
                        .also { onShipmentsEvent(OnSaveRaw(it)) }
                },
                onAddShipments = { data ->
                    val carrier = state.detail.getSuccess()?.first
                    data.mapValues { it.value.shipping.copy(carrierId = carrier) }
                        .also { onShipmentsEvent(OnSaveRaw(it)) }
                },
                cities = citiesState.listing,
            )
        },
        list = {
            CarriersList(
                state = state.listing,
                onDelete = { onEvent(OnDelete(it)) },
                selected = setOf(state.detail.getSuccess()?.first),
                onSelect = { onEvent(OnOpen(it)) },
            )
        },
        snackbarHost = {
            AlarmManagerSnackbar(state = state.snackbar)
            AlarmManagerSnackbar(state = citiesState.snackbar)
            AlarmManagerSnackbar(state = shipmentsState.snackbar)
        }
    )
}
