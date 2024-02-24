package com.chrrissoft.room.ui.components

import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import com.chrrissoft.room.utils.Utils.floatingIcon
import com.chrrissoft.room.shared.view.Page
import com.chrrissoft.room.utils.Utils.uuid

@Composable
fun Page.SaveFloatingActionButton(
    onSave: () -> Unit,
    onCreate: (String) -> Unit,
) {
    FloatingActionButton(
        onClick = {
            when (this) {
                Page.LIST -> onCreate(uuid)
                Page.DETAIL -> onSave()
            }
        },
        content = { Icon(floatingIcon, (null)) }
    )
}