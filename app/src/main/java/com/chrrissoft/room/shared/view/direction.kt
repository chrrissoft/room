package com.chrrissoft.room.shared.view

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chrrissoft.room.cross.db.objects.Direction
import com.chrrissoft.room.ui.components.RoomTextField

@Composable
fun Direction(
    state: Direction,
    onStateChange: (Direction) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        RoomTextField(
            label = "Street",
            value = state.street,
            onValueChange = { onStateChange(state.copy(street = it)) }
        )
        RoomTextField(
            label = "Postal code",
            value = state.postalCode,
            onValueChange = { onStateChange(state.copy(postalCode = it)) }
        )
    }
}