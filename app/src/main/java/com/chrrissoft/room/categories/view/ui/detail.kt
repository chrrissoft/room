package com.chrrissoft.room.categories.view.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chrrissoft.room.categories.db.objects.Category
import com.chrrissoft.room.ui.components.RoomTextField

@Composable
fun Category(
    state: Category,
    onStateChange: (Category) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        RoomTextField(
            value = state.name,
            label = "Name",
            onValueChange = { onStateChange(state.copy(name = it)) },
        )
        RoomTextField(
            value = state.description,
            label = "Description",
            onValueChange = { onStateChange(state.copy(description = it)) })
    }
}
