package com.chrrissoft.room.countries.view.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chrrissoft.room.countries.db.objects.Country
import com.chrrissoft.room.ui.components.RoomTextField

@Composable
fun Country(
    state: Country,
    onStateChange: (Country) -> Unit,
    modifier: Modifier = Modifier,
) {
    RoomTextField(
        value = state.name,
        onValueChange = { onStateChange(state.copy(name = it)) },
        modifier = modifier,
    )
}