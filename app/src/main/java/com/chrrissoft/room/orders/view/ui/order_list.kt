package com.chrrissoft.room.orders.view.ui

import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chrrissoft.room.orders.db.objects.OrderWithRelationship
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.view.ResStateMap
import com.chrrissoft.room.ui.components.SelectableRoomTextField

@Composable
fun OrderList(
    state: ResState<Map<String, OrderWithRelationship>>,
    onSelect: (Pair<String, OrderWithRelationship>) -> Unit,
    onDelete: (Map<String, OrderWithRelationship>) -> Unit,
    selected: Set<String>,
    modifier: Modifier = Modifier,
) {
    ResStateMap(state = state, modifier) {
        items(it.toList()) {
            SelectableRoomTextField(
                value = it.second.order.id,
                onClick = { onSelect(it) },
            )
        }
    }
}
