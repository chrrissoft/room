package com.chrrissoft.room.suppliers.view.ui

import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.view.ResStateMap
import com.chrrissoft.room.suppliers.db.objects.SupplierWithRelationship
import com.chrrissoft.room.ui.components.SelectableRoomTextField

@Composable
fun SuppliersList(
    state: ResState<Map<String, SupplierWithRelationship>>,
    onDelete: (Map<String, SupplierWithRelationship>) -> Unit,
    selected: Set<String>,
    onSelect: (Pair<String, SupplierWithRelationship>) -> Unit,
    modifier: Modifier = Modifier,
) {
    ResStateMap(state, modifier) {
        items(it.toList()) {
            SelectableRoomTextField(value = it.second.supplier.name) {
                onSelect(it)
            }
        }
    }
}