package com.chrrissoft.room.orders.view.ui

import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chrrissoft.room.orders.db.objects.OrderWithRelationship
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.view.Delete
import com.chrrissoft.room.shared.view.ResStateMap
import com.chrrissoft.room.ui.components.SelectableRoomTextField

@Composable
fun OrderList(
    state: ResState<Map<String, OrderWithRelationship>>,
    onSelect: (Pair<String, OrderWithRelationship>) -> Unit,
    selected: Set<String?>,
    modifier: Modifier = Modifier,
    onDelete: ((Map<String, OrderWithRelationship>) -> Unit)? = null,
) {
    ResStateMap(state = state, modifier) { map ->
        items(map.toList()) {
            SelectableRoomTextField(
                value = it.second.order.id,
                selected = selected.contains(it.first),
                onClick = { onSelect(it) },
                trailingIcon = if (onDelete == null) null else Delete { onDelete(mapOf(it)) }
            )
        }
    }
}
