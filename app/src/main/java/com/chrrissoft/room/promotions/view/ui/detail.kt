package com.chrrissoft.room.promotions.view.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chrrissoft.room.promotions.db.objects.Promotion
import com.chrrissoft.room.ui.components.RoomTextField

@Composable
fun Promotion(
    state: Promotion,
    onStateChange: (Promotion) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        RoomTextField(
            label = "Name",
            value = state.name,
            onValueChange = { onStateChange(state.copy(name = it)) }
        )
        RoomTextField(
            label = "Description",
            value = state.description,
            onValueChange = { onStateChange(state.copy(description = it)) })
    }
}
