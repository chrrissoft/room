package com.chrrissoft.room.cities.view.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chrrissoft.room.cities.db.objects.City
import com.chrrissoft.room.ui.components.RoomTextField

@Composable
fun City(
    state: City,
    onStateChange: (City) -> Unit,
    modifier: Modifier = Modifier,
) {
    RoomTextField(
        value = state.name,
        label = "Name",
        onValueChange = { onStateChange(state.copy(name = it)) },
        modifier = modifier
    )
}