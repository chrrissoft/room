package com.chrrissoft.room.costumers.view.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chrrissoft.room.costumers.db.objects.CostumerWithRelationship
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.ui.components.MyModalBottomSheet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AndOrRemoveCostumerListSheet(
    added: ResState<Map<String, CostumerWithRelationship>>,
    available: ResState<Map<String, CostumerWithRelationship>>,
    onRemove: (Map<String, CostumerWithRelationship>) -> Unit,
    onAdd: (Map<String, CostumerWithRelationship>) -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
) {
    MyModalBottomSheet(
        onDismissRequest = onDismissRequest,
        content = { AndOrRemoveCostumerList(added, available, onRemove, onAdd) },
        modifier = modifier
    )
}
