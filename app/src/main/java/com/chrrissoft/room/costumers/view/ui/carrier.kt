package com.chrrissoft.room.costumers.view.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chrrissoft.room.costumers.db.objects.Costumer
import com.chrrissoft.room.shared.view.PersonName

@Composable
fun Costumer(
    state: Costumer,
    onStateChange: (Costumer) -> Unit,
    modifier: Modifier = Modifier,
) {
    PersonName(
        state = state.name,
        onStateChange = { onStateChange(state.copy(name = it)) },
        modifier = modifier,
    )
}