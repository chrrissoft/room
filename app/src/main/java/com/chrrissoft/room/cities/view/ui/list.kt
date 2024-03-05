package com.chrrissoft.room.cities.view.ui

import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chrrissoft.room.cities.db.objects.CityWithRelationship
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.view.Delete
import com.chrrissoft.room.shared.view.ResStateMap
import com.chrrissoft.room.ui.components.SelectableRoomTextField

@Composable
fun CitiesList(
    state: ResState<Map<String, CityWithRelationship>>,
    onSelect: (Pair<String, CityWithRelationship>) -> Unit,
    selected: Set<String?>,
    modifier: Modifier = Modifier,
    onDelete: ((Map<String, CityWithRelationship>) -> Unit)? = null,
) {
    ResStateMap(state = state, modifier) { map ->
        items(map.toList()) {
            SelectableRoomTextField(
                value = it.second.city.name,
                onClick = { onSelect(it) },
                selected = selected.contains(it.first),
                trailingIcon = if (onDelete == null) null else Delete { onDelete(mapOf(it)) }
            )
        }
    }
}
