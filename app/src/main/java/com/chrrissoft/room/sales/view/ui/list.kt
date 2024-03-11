package com.chrrissoft.room.sales.view.ui

import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chrrissoft.room.sales.db.objects.SaleWithRelationship
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.view.Delete
import com.chrrissoft.room.shared.view.ResStateMap
import com.chrrissoft.room.ui.components.SelectableRoomTextField

@Composable
fun SaleList(
    state: ResState<Map<String, SaleWithRelationship>>,
    onSelect: (Pair<String, SaleWithRelationship>) -> Unit,
    selected: Set<String?>,
    modifier: Modifier = Modifier,
    onDelete: ((Map<String, SaleWithRelationship>) -> Unit)? = null,
) {
    ResStateMap(state = state, modifier) { map ->
        items(map.toList()) {
            SelectableRoomTextField(
                value = it.second.sale.name,
                onClick = { onSelect(it) },
                selected = selected.contains(it.first),
                trailingIcon = if (onDelete == null) null else Delete { onDelete(mapOf(it)) }
            )
        }
    }
}
