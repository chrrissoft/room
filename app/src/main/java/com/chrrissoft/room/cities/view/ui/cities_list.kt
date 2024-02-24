package com.chrrissoft.room.cities.view.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.chrrissoft.room.cities.db.objects.CityWithRelationship
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.view.ResStateMap
import com.chrrissoft.room.ui.components.SelectableRoomTextField

@Composable
fun CitiesList(
    state: ResState<Map<String, CityWithRelationship>>,
    onSelect: (Pair<String, CityWithRelationship>) -> Unit,
    onDelete: (Map<String, CityWithRelationship>) -> Unit,
    selected: Set<String>,
    modifier: Modifier = Modifier,
) {
    ResStateMap(state = state, modifier) {
        items(it.toList()) {
            SelectableRoomTextField(
                value = it.second.country.name,
                onClick = { onSelect(it) },
                trailingIcon = {
                    IconButton(onClick = { onDelete(mapOf(it)) }) {
                        Icon(Icons.Rounded.Delete, (null))
                    }
                },
                modifier = Modifier.padding(bottom = 10.dp)
            )
        }
    }
}
