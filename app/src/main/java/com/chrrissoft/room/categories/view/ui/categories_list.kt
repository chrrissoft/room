package com.chrrissoft.room.categories.view.ui

import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chrrissoft.room.categories.db.objects.CategoryWithRelationship
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.view.ResStateMap
import com.chrrissoft.room.ui.components.SelectableRoomTextField

@Composable
fun CategoriesList(
    state: ResState<Map<String, CategoryWithRelationship>>,
    onSelect: (Pair<String, CategoryWithRelationship>) -> Unit,
    onDelete: (Map<String, CategoryWithRelationship>) -> Unit,
    selected: Set<String>,
    modifier: Modifier = Modifier,
) {
    ResStateMap(state, modifier) {
        items(it.toList()) {
            SelectableRoomTextField(
                value = it.second.category.name,
                onClick = { onSelect(it) },
            )
        }
    }
}