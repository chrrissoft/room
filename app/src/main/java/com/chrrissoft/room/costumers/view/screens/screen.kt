package com.chrrissoft.room.costumers.view.screens

import androidx.compose.runtime.Composable
import com.chrrissoft.room.cities.view.states.CitiesState
import com.chrrissoft.room.costumers.db.objects.Costumer
import com.chrrissoft.room.costumers.db.objects.CostumerWithRelationship
import com.chrrissoft.room.costumers.view.events.CostumersEvent
import com.chrrissoft.room.costumers.view.states.CostumersState
import com.chrrissoft.room.costumers.view.ui.CostumerWithRelationship
import com.chrrissoft.room.costumers.view.ui.CostumersList
import com.chrrissoft.room.ui.components.AlarmManagerSnackbar
import com.chrrissoft.room.ui.components.CommonScreen
import com.chrrissoft.room.utils.ResStateUtils.getSuccess

@Composable
fun CostumersScreen(
    state: CostumersState,
    citiesState: CitiesState,
    onEvent: (CostumersEvent) -> Unit,
    onOpenDrawer: () -> Unit,
) {
        CommonScreen(
            page = state.page,
            title = "Costumers",
            onChangePage = { onEvent(CostumersEvent.OnChangePage(it)) },
            onSave = { state.costumer.getSuccess()?.let { onEvent(CostumersEvent.OnSave(it)) } },
            onCreate = { onEvent(CostumersEvent.OnCreate(it to CostumerWithRelationship(Costumer(it)))) },
            onNavigation = onOpenDrawer,
            details = {
                CostumerWithRelationship(
                    state = state.costumer,
                    onStateChange = { onEvent(CostumersEvent.OnChange(it)) },
                    cities = citiesState.cities,
                )
            },
            list = {
                CostumersList(
                    state = state.costumers,
                    onDelete = { onEvent(CostumersEvent.OnDelete(it)) },
                    selected = setOf(),
                    onSelect = { onEvent(CostumersEvent.OnOpen(it.first)) },
                )
            },
            snackbarHost = { AlarmManagerSnackbar(state = state.snackbar) }
        )
}

