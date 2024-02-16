package com.chrrissoft.room.shared.view

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chrrissoft.room.common.Direction
import com.chrrissoft.room.ui.components.RoomTextField

@Composable
fun Direction(
    state: Direction,
    onStateChange: (Direction) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        RoomTextField(
            value = state.street,
            onValueChange = { onStateChange(state.copy(street = it)) }
        )
        RoomTextField(
            value = state.postalCode,
            onValueChange = { onStateChange(state.copy(postalCode = it)) }
        )
    }
}