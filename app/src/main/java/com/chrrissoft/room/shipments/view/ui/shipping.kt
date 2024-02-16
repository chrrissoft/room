package com.chrrissoft.room.shipments.view.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chrrissoft.room.shipments.db.objects.Shipping

@Composable
fun Shipping(
    state: Shipping,
    onStateChange: (Shipping) -> Unit,
    modifier: Modifier = Modifier,
) {
    ShippingState(
        state = state.state,
        onStateChange = { onStateChange(state.copy(state = it)) },
        modifier = modifier
    )
}