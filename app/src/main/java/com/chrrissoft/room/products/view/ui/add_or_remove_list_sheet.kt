package com.chrrissoft.room.products.view.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chrrissoft.room.products.db.objects.ProductWithRelationship
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.ui.components.MyModalBottomSheet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AndOrRemoveProductListSheet(
    added: ResState<Map<String, ProductWithRelationship>>,
    available: ResState<Map<String, ProductWithRelationship>>,
    onRemove: (Map<String, ProductWithRelationship>) -> Unit,
    onAdd: (Map<String, ProductWithRelationship>) -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
) {
    MyModalBottomSheet(
        onDismissRequest = onDismissRequest,
        content = { AndOrRemoveProductList(added, available, onRemove, onAdd) },
        modifier = modifier
    )
}
