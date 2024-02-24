package com.chrrissoft.room.orders.view.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chrrissoft.room.orders.db.objects.OrderWithRelationship
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.ui.components.MyModalBottomSheet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderListSheet(
    state: ResState<Map<String, OrderWithRelationship>>,
    onSelect: (Pair<String, OrderWithRelationship>) -> Unit,
    onDelete: (Map<String, OrderWithRelationship>) -> Unit,
    selected: Set<String>,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
) {
    MyModalBottomSheet(
        onDismissRequest = onDismissRequest,
        content = { OrderList(state, onSelect, onDelete, selected) },
        modifier = modifier
    )
}
