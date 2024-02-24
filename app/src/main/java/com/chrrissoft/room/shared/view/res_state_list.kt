package com.chrrissoft.room.shared.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import com.chrrissoft.room.shared.app.ResState

@Composable
fun <T> ResStateList(
    state: ResState<List<T>>,
    modifier: Modifier = Modifier,
    content: LazyListScope.(List<T>) -> Unit,
) {
    Box(modifier.fillMaxSize(), contentAlignment = Center) {
        when (state) {
            ResState.None -> Text(text = "None")
            ResState.Loading -> Text(text = "Loading")
            is ResState.Error -> Text(text = "Error: ${state.message}")
            is ResState.Success -> {
                if (state.data.isEmpty()) Text(text = "Empty")
                else LazyColumn(Modifier.fillMaxSize()) { content(state.data) }
            }
        }
    }
}
