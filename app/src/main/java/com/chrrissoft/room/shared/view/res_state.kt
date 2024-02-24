package com.chrrissoft.room.shared.view

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chrrissoft.room.shared.app.ResState

@Composable
fun <T> ResState(
    state: ResState<T>,
    modifier: Modifier = Modifier,
    content: @Composable (T) -> Unit,
) {
    Box(modifier) {
        when (state) {
            ResState.None -> Text(text = "None")
            ResState.Loading -> Text(text = "Loading")
            is ResState.Error -> Text(text = "Error: ${state.message}")
            is ResState.Success -> content(state.data)
        }
    }
}
