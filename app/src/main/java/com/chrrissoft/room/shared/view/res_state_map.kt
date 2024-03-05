package com.chrrissoft.room.shared.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.Modifier
import com.chrrissoft.room.shared.app.ResState

@Composable
fun <K, V> ResStateMap(
    state: ResState<Map<K, V>>,
    modifier: Modifier = Modifier,
    content: LazyListScope.(Map<K, V>) -> Unit,
) {
    Box(modifier.fillMaxSize(), contentAlignment = TopCenter) {
        when (state) {
            ResState.None -> Text(text = "None", color = colorScheme.primary)
            ResState.Loading -> Text(text = "Loading", color = colorScheme.primary)
            is ResState.Error -> Text(text = "Error: ${state.message}", color = colorScheme.primary)
            is ResState.Success -> {
                if (state.data.isEmpty()) Text(text = "Empty", color = colorScheme.primary)
                else LazyColumn(Modifier.fillMaxSize()) { content(state.data) }
            }
        }
    }
}
