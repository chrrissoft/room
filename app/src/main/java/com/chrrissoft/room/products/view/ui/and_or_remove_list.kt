package com.chrrissoft.room.products.view.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chrrissoft.room.products.db.objects.ProductWithRelationship
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.ui.components.RoomDivider
import com.chrrissoft.room.ui.components.SelectableRoomTextField
import com.chrrissoft.room.utils.ResStateUtils.getSuccess

@Composable
fun AndOrRemoveProductList(
    added: ResState<Map<String, ProductWithRelationship>>,
    available: ResState<Map<String, ProductWithRelationship>>,
    onRemove: (Map<String, ProductWithRelationship>) -> Unit,
    onAdd: (Map<String, ProductWithRelationship>) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier) {
        items(added.getSuccess()?.toList() ?: emptyList()) {
            SelectableRoomTextField(
                value = it.second.product.name,
                selected = true,
                onClick = {  },
                trailingIcon = {
                    IconButton(
                        onClick = { onRemove(mapOf(it)) },
                        content = { Icon(Icons.Rounded.Remove, (null)) },
                    )
                }
            )
        }

        item { RoomDivider() }

        items(available.getSuccess()?.toList() ?: emptyList()) {
            SelectableRoomTextField(
                value = it.second.product.name,
                selected = false,
                onClick = {  },
                trailingIcon = {
                    IconButton(
                        onClick = { onAdd(mapOf(it)) },
                        content = { Icon(Icons.Rounded.Add, (null)) },
                    )
                }
            )
        }
    }
}
