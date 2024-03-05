package com.chrrissoft.room.products.view.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chrrissoft.room.products.db.objects.ProductWithRelationship
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.ui.components.MyModalBottomSheet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListSheet(
    state: ResState<Map<String,ProductWithRelationship>>,
    selected: Set<String?>,
    onSelect: (Pair<String, ProductWithRelationship>) -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    onDelete: ((Map<String, ProductWithRelationship>) -> Unit)? = null,
) {
    MyModalBottomSheet(
        onDismissRequest = onDismissRequest,
        content = { ProductList(state, selected, onSelect, onDelete = onDelete) },
        modifier = modifier
    )
}
