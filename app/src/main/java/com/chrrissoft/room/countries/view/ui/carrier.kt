package com.chrrissoft.room.countries.view.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chrrissoft.room.countries.db.objects.Country
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.view.ResState
import com.chrrissoft.room.ui.components.RoomTextField

@Composable
fun Country(
    state: ResState<Country>,
    onStateChange: (Country) -> Unit,
    modifier: Modifier = Modifier,
) = ResState(state, modifier) { Country(state = it, onStateChange = onStateChange) }

@Composable
fun Country(
    state: Country,
    onStateChange: (Country) -> Unit,
    modifier: Modifier = Modifier,
) {
    RoomTextField(
        value = state.name,
        label = "Name",
        onValueChange = { onStateChange(state.copy(name = it)) },
        modifier = modifier,
    )
}