package com.chrrissoft.room.products.view.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chrrissoft.room.products.db.objects.Product
import com.chrrissoft.room.ui.components.RoomTextField

@Composable
fun Product(
    state: Product,
    onStateChange: (Product) -> Unit,
    modifier: Modifier = Modifier
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