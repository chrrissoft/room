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
            value = state.firstName,
            onValueChange = { onStateChange(state.copy(firstName = it)) },
        )

        RoomTextField(
            value = state. middleName,
            onValueChange = { onStateChange(state.copy(middleName = it)) },
        )

        RoomTextField(
            value = state.surname,
            onValueChange = { onStateChange(state.copy(surname = it)) },
        )
    }
}
