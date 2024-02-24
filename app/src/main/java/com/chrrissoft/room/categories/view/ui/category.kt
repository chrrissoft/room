package com.chrrissoft.room.categories.view.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.chrrissoft.room.categories.db.objects.Category
import com.chrrissoft.room.ui.components.RoomTextField

@Composable
fun Category(
    state: Category,
    onStateChange: (Category) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        RoomTextField(value = state.name, onValueChange = { onStateChange(state.copy(name = it)) })
        Spacer(modifier = Modifier.height(10.dp))
        RoomTextField(
            value = state.description,
            onValueChange = { onStateChange(state.copy(description = it)) })
    }
}
