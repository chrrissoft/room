package com.chrrissoft.room.shared.view

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chrrissoft.room.shared.db.PersonName
import com.chrrissoft.room.ui.components.RoomTextField

@Composable
fun PersonName(
    state: PersonName,
    onStateChange: (PersonName) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        RoomTextField(
            value = state.first,
            label = "First name",
            onValueChange = { onStateChange(state.copy(first = it)) },
        )
        RoomTextField(
            value = state. middle,
            label = "Middle name",
            onValueChange = { onStateChange(state.copy(middle = it)) },
        )
        RoomTextField(
            value = state.surname,
            label = "Surname",
            onValueChange = { onStateChange(state.copy(surname = it)) },
        )
    }
}
