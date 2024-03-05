package com.chrrissoft.room.categories.view.ui

import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chrrissoft.room.categories.db.objects.CategoryWithRelationship
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.view.Delete
import com.chrrissoft.room.shared.view.ResStateMap
import com.chrrissoft.room.ui.components.SelectableRoomTextField

@Composable
fun CategoriesList(
    state: ResState<Map<String, CategoryWithRelationship>>,
    selected: Set<String?>,
    onSelect: (Pair<String, CategoryWithRelationship>) -> Unit,
    modifier: Modifier = Modifier,
    onDelete: ((Map<String, CategoryWithRelationship>) -> Unit)? = null,
) {
    ResStateMap(state, modifier) { map ->
        items(map.toList()) {
            SelectableRoomTextField(
                value = it.second.category.name,
                selected = selected.contains(it.first),
                onClick = { onSelect(it) },
                trailingIcon = if (onDelete == null) null else Delete { onDelete(mapOf(it)) }
            )
        }
    }
}
