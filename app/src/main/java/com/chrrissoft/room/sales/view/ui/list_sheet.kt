package com.chrrissoft.room.sales.view.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chrrissoft.room.sales.db.objects.SaleWithRelationship
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.ui.components.MyModalBottomSheet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SaleListSheet(
    state: ResState<Map<String, SaleWithRelationship>>,
    onSelect: (Pair<String, SaleWithRelationship>) -> Unit,
    selected: Set<String?>,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    onDelete: ((Map<String, SaleWithRelationship>) -> Unit)? = null,
) {
    MyModalBottomSheet(
        onDismissRequest = onDismissRequest,
        content = { SaleList(state, onSelect, selected,onDelete = onDelete) },
        modifier = modifier
    )
}
