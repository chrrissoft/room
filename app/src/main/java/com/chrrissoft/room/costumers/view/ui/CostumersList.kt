package com.chrrissoft.room.costumers.view.ui

import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chrrissoft.room.costumers.db.objects.CostumerWithRelationship
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.view.ResStateMap
import com.chrrissoft.room.ui.components.SelectableRoomTextField

@Composable
fun CostumersList(
    state: ResState<Map<String, CostumerWithRelationship>>,
    onSelect: (Pair<String, CostumerWithRelationship>) -> Unit,
    onDelete: (Map<String, CostumerWithRelationship>) -> Unit,
    selected: Set<String>,
    modifier: Modifier = Modifier,
) {
    ResStateMap(state, modifier) {
        items(it.toList()) {
            SelectableRoomTextField(value = it.second.costumer.name.firstName) {
                onSelect(it)
            }
        }
    }
}
