package com.chrrissoft.room.promotions.view.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chrrissoft.room.promotions.db.objects.PromotionWithRelationship
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.ui.components.RoomDivider
import com.chrrissoft.room.ui.components.SelectableRoomTextField
import com.chrrissoft.room.utils.ResStateUtils.getSuccess

@Composable
fun AndOrRemovePromotionList(
    added: ResState<Map<String, PromotionWithRelationship>>,
    available: ResState<Map<String, PromotionWithRelationship>>,
    onRemove: (Map<String, PromotionWithRelationship>) -> Unit,
    onAdd: (Map<String, PromotionWithRelationship>) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier) {
        items(added.getSuccess()?.toList() ?: emptyList()) {
            SelectableRoomTextField(
                value = it.second.promotion.name,
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
                value = it.second.promotion.name,
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
