package com.chrrissoft.room.sales.view.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chrrissoft.room.sales.db.objects.SaleWithRelationship
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.view.ResStateMap

@Composable
fun SaleList(
    state: ResState<Map<String, SaleWithRelationship>>,
    onSelect: (Pair<String, SaleWithRelationship>) -> Unit,
    onDelete: (Map<String, SaleWithRelationship>) -> Unit,
    selected: Set<String>,
    modifier: Modifier = Modifier,
) {
    ResStateMap(state = state, modifier) {

    }
}
