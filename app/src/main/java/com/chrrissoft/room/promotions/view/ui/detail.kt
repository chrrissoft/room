package com.chrrissoft.room.promotions.view.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
        Spacer(modifier = Modifier.height(10.dp))
        RoomTextField(
            label = "Description",
            value = state.description,
            onValueChange = { onStateChange(state.copy(description = it)) })
    }
}