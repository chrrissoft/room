package com.chrrissoft.room.shipments.view.ui

import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.view.ResStateMap
import com.chrrissoft.room.shipments.db.objects.ShippingWithRelationship
import com.chrrissoft.room.ui.components.SelectableRoomTextField

@Composable
fun ShipmentsList(
    state: ResState<Map<String, ShippingWithRelationship>>,
    onDelete: (Map<String, ShippingWithRelationship>) -> Unit,
    onSelect: (Pair<String, ShippingWithRelationship>) -> Unit,
    selected: Set<String>,
    modifier: Modifier = Modifier,
) {
    ResStateMap(state, modifier) {
        items(it.toList()) {
            SelectableRoomTextField(value = it.second.shipping.state.name) {
                onSelect(it)
            }
        }
    }
}
