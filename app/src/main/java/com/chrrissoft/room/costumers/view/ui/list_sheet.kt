package com.chrrissoft.room.costumers.view.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chrrissoft.room.costumers.db.objects.CostumerWithRelationship
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.ui.components.MyModalBottomSheet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CostumerListSheet(
    state: ResState<Map<String, CostumerWithRelationship>>,
    selected: Set<String?>,
    onSelect: (Pair<String, CostumerWithRelationship>) -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    onDelete: ((Map<String, CostumerWithRelationship>) -> Unit)? = null,
) {
    MyModalBottomSheet(
        onDismissRequest = onDismissRequest,
        content = { CostumersList(state, onSelect, selected, onDelete = onDelete) },
        modifier = modifier
    )
}
