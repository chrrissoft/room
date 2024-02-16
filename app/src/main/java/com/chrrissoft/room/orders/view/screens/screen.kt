package com.chrrissoft.room.orders.view.screens

import androidx.compose.runtime.Composable
import com.chrrissoft.room.orders.view.events.OrdersEvent
import com.chrrissoft.room.orders.view.states.OrdersState

@Composable
fun OrdersScreen(
    state: OrdersState,
    onEvent: (OrdersEvent) -> Unit,
    onOpenDrawer: () -> Unit,
) {

}
