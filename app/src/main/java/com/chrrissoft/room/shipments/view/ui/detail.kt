package com.chrrissoft.room.shipments.view.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chrrissoft.room.shipments.db.objects.Shipping
import com.chrrissoft.room.ui.components.RoomTextField

@Composable
fun Shipping(
    state: Shipping,
    onStateChange: (Shipping) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        RoomTextField(
            label = "Name",
            value = state.name,
            onValueChange = { onStateChange(state.copy(name = it)) }
        )
        ShippingState(
            state = state.state,
            onStateChange = { onStateChange(state.copy(state = it)) },
        )
    }
}