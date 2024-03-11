package com.chrrissoft.room.suppliers.view.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chrrissoft.room.shared.view.Direction
import com.chrrissoft.room.suppliers.db.objects.Supplier
import com.chrrissoft.room.ui.components.RoomTextField

@Composable
fun Supplier(
    state: Supplier,
    onStateChange: (Supplier) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        RoomTextField(
            label = "Name",
            value = state.name,
            onValueChange = { onStateChange(state.copy(name = it)) }
        )
        Direction(
            state = state.direction,
            onStateChange = { onStateChange(state.copy(direction = it)) })
    }
}
