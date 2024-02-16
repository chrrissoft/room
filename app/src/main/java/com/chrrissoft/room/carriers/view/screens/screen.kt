package com.chrrissoft.room.carriers.view.screens

import androidx.compose.runtime.Composable
import com.chrrissoft.room.carriers.view.events.CarriersEvent
import com.chrrissoft.room.carriers.view.states.CarriersState
import com.chrrissoft.room.ui.components.Screen

@Composable
fun CarriersScreen(
    state: CarriersState,
    onEvent: (CarriersEvent) -> Unit,
    onOpenDrawer: () -> Unit,
) {
    Screen(title = "Carriers", onNavigation = onOpenDrawer) {

    }
}
