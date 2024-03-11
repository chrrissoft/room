package com.chrrissoft.room.orders.view.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chrrissoft.room.orders.db.objects.OrderWithRelationship
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.ui.components.MyModalBottomSheet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AndOrRemoveOrderListSheet(
    added: ResState<Map<String, OrderWithRelationship>>,
    available: ResState<Map<String, OrderWithRelationship>>,
    onRemove: ((Map<String, OrderWithRelationship>) -> Unit)?,
    onAdd: (Map<String, OrderWithRelationship>) -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
) {
    MyModalBottomSheet(
        onDismissRequest = onDismissRequest,
        content = { AndOrRemoveOrderList(added, available, onRemove, onAdd) },
        modifier = modifier
    )
}
