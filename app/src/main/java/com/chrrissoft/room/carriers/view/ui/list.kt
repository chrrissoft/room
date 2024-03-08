package com.chrrissoft.room.carriers.view.ui

import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chrrissoft.room.carriers.db.objects.CarrierWithRelationship
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.view.Delete
import com.chrrissoft.room.shared.view.ResStateMap
import com.chrrissoft.room.ui.components.SelectableRoomTextField

@Composable
fun CarriersList(
    state: ResState<Map<String, CarrierWithRelationship>>,
    selected: Set<String?>,
    onSelect: (Pair<String, CarrierWithRelationship>) -> Unit,
    modifier: Modifier = Modifier,
    onDelete: ((Map<String, CarrierWithRelationship>) -> Unit)? = null,
) {
    ResStateMap(state, modifier) { map ->
        items(map.toList()) {
            SelectableRoomTextField(
                value = it.second.carrier.name.first,
                selected = selected.contains(it.first),
                onClick = { onSelect(it) },
                trailingIcon = if (onDelete == null) null else Delete { onDelete(mapOf(it)) }
            )
        }
    }
}
