package com.chrrissoft.room.countries.view.ui

import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chrrissoft.room.countries.db.objects.CountryWithRelationship
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.view.Delete
import com.chrrissoft.room.shared.view.ResStateMap
import com.chrrissoft.room.ui.components.SelectableRoomTextField

@Composable
fun CountriesList(
    state: ResState<Map<String, CountryWithRelationship>>,
    onSelect: (Pair<String, CountryWithRelationship>) -> Unit,
    selected: Set<String?>,
    modifier: Modifier = Modifier,
    onDelete: ((Map<String, CountryWithRelationship>) -> Unit)? = null,
) {
    ResStateMap(state = state, modifier) { res ->
        items(res.toList()) {
            SelectableRoomTextField(
                value = it.second.country.name,
                onClick = { onSelect(it) },
                selected = selected.contains(it.first),
                trailingIcon = if (onDelete == null) null else Delete { onDelete(mapOf(it)) }
            )
        }
    }
}
