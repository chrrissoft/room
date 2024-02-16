package com.chrrissoft.room.sales.view.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chrrissoft.room.sales.db.objects.Sale
import com.chrrissoft.room.ui.components.RoomTextField

@Composable
fun Sale(
    state: Sale,
    onStateChange: (Sale) -> Unit,
    modifier: Modifier = Modifier,
) {
    RoomTextField(
        value = state.description,
        onValueChange = { onStateChange(state.copy(description = it)) },
        modifier = modifier,
    )
}
