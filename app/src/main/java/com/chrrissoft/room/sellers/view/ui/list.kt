package com.chrrissoft.room.sellers.view.ui

import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chrrissoft.room.sellers.db.objects.SellerWithRelationship
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.view.Delete
import com.chrrissoft.room.shared.view.ResStateMap
import com.chrrissoft.room.ui.components.SelectableRoomTextField

@Composable
fun SellersList(
    state: ResState<Map<String, SellerWithRelationship>>,
    onSelect: (Pair<String, SellerWithRelationship>) -> Unit,
    selected: Set<String?>,
    modifier: Modifier = Modifier,
    onDelete: ((Map<String, SellerWithRelationship>) -> Unit)? = null,
) {
    ResStateMap(state = state, modifier) { it ->
        items(it.toList()) {
            SelectableRoomTextField(
                value = it.second.seller.name.first,
                selected = selected.contains(it.first),
                onClick = { onSelect(it) },
                trailingIcon = if (onDelete == null) null else Delete { onDelete(mapOf(it)) }
            )
        }
    }
}
