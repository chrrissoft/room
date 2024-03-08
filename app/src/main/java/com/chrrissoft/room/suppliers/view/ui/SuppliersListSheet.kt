package com.chrrissoft.room.suppliers.view.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.suppliers.db.objects.SupplierWithRelationship
import com.chrrissoft.room.ui.components.MyModalBottomSheet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuppliersListSheet(
    state: ResState<Map<String, SupplierWithRelationship>>,
    onSelect: (Pair<String, SupplierWithRelationship>) -> Unit,
    selected: Set<String>,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
) {
    MyModalBottomSheet(
        content = { SuppliersList(state, selected, onSelect) },
        onDismissRequest = onDismissRequest,
        modifier = modifier
    )
}