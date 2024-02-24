package com.chrrissoft.room.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RoomDivider() {
    Divider(
        color = MaterialTheme.colorScheme.primary.copy(.5f),
        modifier = Modifier.padding(vertical = 15.dp)
    )
}
