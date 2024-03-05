package com.chrrissoft.room.orders.view.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chrrissoft.room.orders.db.objects.Order
import com.chrrissoft.room.shared.view.Direction

@Composable
fun Order(
    state: Order,
    onStateChange: (Order) -> Unit,
    modifier: Modifier = Modifier,
) {
    Direction(
        state = state.direction,
        onStateChange = { onStateChange(state.copy(direction = it)) },
        modifier = modifier
    )
}
