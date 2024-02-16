package com.chrrissoft.room.orders.view.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chrrissoft.room.orders.db.objects.OrderWithRelationship
import com.chrrissoft.room.ui.components.MyModalBottomSheet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderListSheet(
    state: List<OrderWithRelationship>,
    onSelect: (OrderWithRelationship) -> Unit,
    selected: Set<String>,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
) {
    MyModalBottomSheet(
        onDismissRequest = onDismissRequest,
        content = { OrderList(state = state, onSelect = onSelect, selected = selected) },
        modifier = modifier
    )
}
