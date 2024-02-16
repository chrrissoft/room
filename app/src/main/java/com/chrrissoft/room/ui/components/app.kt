package com.chrrissoft.room.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chrrissoft.room.ui.theme.RoomTheme

@Composable
fun App(app: @Composable () -> Unit) {
    RoomTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.onPrimary
        ) { app() }
    }
}
