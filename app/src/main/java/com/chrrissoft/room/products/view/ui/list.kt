package com.chrrissoft.room.products.view.ui

import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chrrissoft.room.products.db.objects.ProductWithRelationship
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.view.Delete
import com.chrrissoft.room.shared.view.ResStateMap
import com.chrrissoft.room.ui.components.SelectableRoomTextField

@Composable
fun ProductList(
    state: ResState<Map<String, ProductWithRelationship>>,
    selected: Set<String?>,
    onSelect: (Pair<String, ProductWithRelationship>) -> Unit,
    modifier: Modifier = Modifier,
    onDelete: ((Map<String, ProductWithRelationship>) -> Unit)? = null,
) {
    ResStateMap(state, modifier) { map ->
        items(map.toList()) {
            SelectableRoomTextField(
                value = it.second.product.name,
                onClick = { onSelect(it) },
                selected = selected.contains(it.first),
                trailingIcon = if (onDelete == null) null else Delete { onDelete(mapOf(it)) }
            )
        }
    }
}
