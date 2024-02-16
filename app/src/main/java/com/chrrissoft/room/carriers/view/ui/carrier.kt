package com.chrrissoft.room.carriers.view.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chrrissoft.room.carriers.db.objects.Carrier
import com.chrrissoft.room.shared.view.PersonName

@Composable
fun Carrier(
    state: Carrier,
    onStateChange: (Carrier) -> Unit,
    modifier: Modifier = Modifier,
) {
    PersonName(
        state = state.name,
        onStateChange = { onStateChange(state.copy(name = it)) },
        modifier = modifier,
    )
}