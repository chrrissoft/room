package com.chrrissoft.room.sales.view.ui

import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chrrissoft.room.sales.db.objects.SaleWithRelationship
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.view.ResStateMap
import com.chrrissoft.room.ui.components.SelectableRoomTextField

@Composable
fun SalesList(
    state: ResState<Map<String, SaleWithRelationship>>,
    onDelete: (Map<String, SaleWithRelationship>) -> Unit,
    selected: Set<String>,
    onSelect: (Pair<String, SaleWithRelationship>) -> Unit,
    modifier: Modifier = Modifier
) {
    ResStateMap(state, modifier) {
        items(it.toList()) {
            SelectableRoomTextField(value = it.second.sale.id) {
                onSelect(it)
            }
        }
    }
}