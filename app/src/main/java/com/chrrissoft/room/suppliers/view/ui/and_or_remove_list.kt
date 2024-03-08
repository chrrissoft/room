package com.chrrissoft.room.suppliers.view.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chrrissoft.room.suppliers.db.objects.SupplierWithRelationship
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.ui.components.RoomDivider
import com.chrrissoft.room.ui.components.SelectableRoomTextField
import com.chrrissoft.room.utils.ResStateUtils.getSuccess

@Composable
fun AndOrRemoveSupplierList(
    added: ResState<Map<String, SupplierWithRelationship>>,
    available: ResState<Map<String, SupplierWithRelationship>>,
    onRemove: (Map<String, SupplierWithRelationship>) -> Unit,
    onAdd: (Map<String, SupplierWithRelationship>) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier) {
        items(added.getSuccess()?.toList() ?: emptyList()) {
            SelectableRoomTextField(
                value = it.second.supplier.name,
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
                value = it.second.supplier.name,
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
