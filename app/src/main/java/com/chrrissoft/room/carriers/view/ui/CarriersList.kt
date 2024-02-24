package com.chrrissoft.room.carriers.view.ui

import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chrrissoft.room.carriers.db.objects.CarrierWithRelationship
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.view.ResStateMap
import com.chrrissoft.room.ui.components.SelectableRoomTextField

@Composable
fun CarriersList(
    state: ResState<Map<String, CarrierWithRelationship>>,
    onDelete: (Map<String, CarrierWithRelationship>) -> Unit,
    selected: Set<String>,
    onSelect: (Pair<String, CarrierWithRelationship>) -> Unit,
    modifier: Modifier = Modifier,
) {
    ResStateMap(state, modifier) {
        items(it.toList()) {
            SelectableRoomTextField(value = it.second.carrier.name.firstName) {
                onSelect(it)
            }
        }
    }
}