package com.chrrissoft.room.shared.view

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.DeleteForever
import androidx.compose.material.icons.rounded.Remove
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@Composable
fun DeleteIcon(
    onDelete: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var showDialog by rememberSaveable { mutableStateOf(value = false) }

    if (showDialog) {
        AlertDialog(
            icon = { Icon(Icons.Rounded.DeleteForever, (null)) },
            title = { Text(text = "Delete?") },
            onDismissRequest = { showDialog = false },
            text = { Text(text = "Are you sure to delete the this data?") },
            confirmButton = { Button(onClick = { onDelete() }) { Text(text = "Yes") } },
            dismissButton = { Button(onClick = { showDialog = false }) { Text(text = "Not") } },
        )
    }

    IconButton(
        onClick = { showDialog = true },
        content = { Icon(Icons.Rounded.Delete, (null)) },
        modifier = modifier
    )
}

@Composable
fun Delete(onDelete: () -> Unit) = @Composable { DeleteIcon(onDelete = onDelete) }

@Composable
fun RemoveIcon(
    onRemove: () -> Unit,
    modifier: Modifier = Modifier,
) {
    IconButton(
        onClick = { onRemove() },
        content = { Icon(Icons.Rounded.Remove, (null)) },
        modifier = modifier
    )
}

@Composable
fun Remove(onRemove: () -> Unit) = @Composable { RemoveIcon(onRemove = onRemove) }
