package com.chrrissoft.room.sellers.view.ui

import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chrrissoft.room.sellers.db.objects.SellerWithRelationship
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.view.ResStateMap
import com.chrrissoft.room.ui.components.SelectableRoomTextField

@Composable
fun SellersList(
    state: ResState<Map<String, SellerWithRelationship>>,
    onSelect: (Pair<String, SellerWithRelationship>) -> Unit,
    onDelete: (Map<String, SellerWithRelationship>) -> Unit,
    selected: Set<String>,
    modifier: Modifier = Modifier
) {
    ResStateMap(state = state, modifier) {
        items(it.toList()) {
            SelectableRoomTextField(
                value = it.second.seller.name.firstName,
                onClick = { onSelect(it) },
            )
        }
    }
}
