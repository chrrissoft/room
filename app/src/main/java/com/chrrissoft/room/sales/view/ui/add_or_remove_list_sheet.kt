package com.chrrissoft.room.sales.view.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chrrissoft.room.sales.db.objects.SaleWithRelationship
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.ui.components.MyModalBottomSheet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AndOrRemoveSaleListSheet(
    added: ResState<Map<String, SaleWithRelationship>>,
    available: ResState<Map<String, SaleWithRelationship>>,
    onRemove: ((Map<String, SaleWithRelationship>) -> Unit)?,
    onAdd: (Map<String, SaleWithRelationship>) -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
) {
    MyModalBottomSheet(
        onDismissRequest = onDismissRequest,
        content = { AndOrRemoveSaleList(added, available, onRemove, onAdd) },
        modifier = modifier
    )
}
