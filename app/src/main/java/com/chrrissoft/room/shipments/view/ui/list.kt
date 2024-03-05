package com.chrrissoft.room.shipments.view.ui

import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.view.Delete
import com.chrrissoft.room.shared.view.ResStateMap
import com.chrrissoft.room.shipments.db.objects.ShippingWithRelationship
import com.chrrissoft.room.ui.components.SelectableRoomTextField

@Composable
fun ShipmentsList(
    state: ResState<Map<String, ShippingWithRelationship>>,
    onSelect: (Pair<String, ShippingWithRelationship>) -> Unit,
    selected: Set<String?>,
    modifier: Modifier = Modifier,
    onDelete: ((Map<String, ShippingWithRelationship>) -> Unit)? = null,
) {
    ResStateMap(state, modifier) { map ->
        items(map.toList()) {
            SelectableRoomTextField(
                value = it.second.shipping.state.name,
                selected = selected.contains(it.first),
                onClick = { onSelect(it) },
                trailingIcon = if (onDelete == null) null else Delete { onDelete(mapOf(it)) }
            )
        }
    }
}
