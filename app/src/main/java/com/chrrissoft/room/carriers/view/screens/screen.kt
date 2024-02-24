package com.chrrissoft.room.carriers.view.screens

import androidx.compose.runtime.Composable
import com.chrrissoft.room.carriers.view.events.CarriersEvent
import com.chrrissoft.room.carriers.view.states.CarriersState
import com.chrrissoft.room.carriers.view.ui.CarrierWithRelationship
import com.chrrissoft.room.cities.view.states.CitiesState
import com.chrrissoft.room.carriers.db.objects.Carrier
import com.chrrissoft.room.carriers.db.objects.CarrierWithRelationship
import com.chrrissoft.room.carriers.view.ui.CarriersList
import com.chrrissoft.room.ui.components.AlarmManagerSnackbar
import com.chrrissoft.room.ui.components.CommonScreen
import com.chrrissoft.room.utils.ResStateUtils.getSuccess

@Composable
fun CarriersScreen(
    state: CarriersState,
    citiesState: CitiesState,
    onEvent: (CarriersEvent) -> Unit,
    onOpenDrawer: () -> Unit,
) {
    CommonScreen(
        page = state.page,
        title = "Carriers",
        onChangePage = { onEvent(CarriersEvent.OnChangePage(it)) },
        onSave = { state.carrier.getSuccess()?.let { onEvent(CarriersEvent.OnSave(it)) } },
        onCreate = { onEvent(CarriersEvent.OnCreate(it to CarrierWithRelationship(Carrier(it)))) },
        onNavigation = onOpenDrawer,
        details = {
            CarrierWithRelationship(
                state = state.carrier,
                onStateChange = { onEvent(CarriersEvent.OnChange(it)) },
                cities = citiesState.cities,
            )
        },
        list = {
            CarriersList(
                state = state.carriers,
                onDelete = { onEvent(CarriersEvent.OnDelete(it)) },
                selected = setOf(),
                onSelect = { onEvent(CarriersEvent.OnOpen(it.first)) },
            )
        },
        snackbarHost = { AlarmManagerSnackbar(state = state.snackbar) }
    )
}
