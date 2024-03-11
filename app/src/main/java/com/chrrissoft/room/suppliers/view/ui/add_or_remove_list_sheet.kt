package com.chrrissoft.room.suppliers.view.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chrrissoft.room.suppliers.db.objects.SupplierWithRelationship
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.ui.components.MyModalBottomSheet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AndOrRemoveSupplierListSheet(
    added: ResState<Map<String, SupplierWithRelationship>>,
    available: ResState<Map<String, SupplierWithRelationship>>,
    onRemove: ((Map<String, SupplierWithRelationship>) -> Unit)?,
    onAdd: (Map<String, SupplierWithRelationship>) -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
) {
    MyModalBottomSheet(
        onDismissRequest = onDismissRequest,
        content = { AndOrRemoveSupplierList(added, available, onRemove, onAdd) },
        modifier = modifier
    )
}
