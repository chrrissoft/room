package com.chrrissoft.room.ui.components

/*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.chrrissoft.room.entities.ResState
import com.chrrissoft.room.entities.ResState.Error
import com.chrrissoft.room.entities.ResState.Loading
import com.chrrissoft.room.entities.ResState.Success

@Composable
fun <T> Page(
    state: ResState<T>,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.(T) -> Unit,
) {
    Column(modifier = modifier) {
        when (state) {
            Loading -> {
                Text(
                    text = "Loading...",
                    textAlign = TextAlign.Center,
                    style = typography.titleMedium,
                    color = colorScheme.onPrimaryContainer,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            is Success -> content(state.data)
            is Error -> {
                Card(
                    title = "Error",
                    colors = cardColors(colorScheme.errorContainer, colorScheme.onErrorContainer)
                ) { Text(state.message) }
            }
        }
    }
}
*/
