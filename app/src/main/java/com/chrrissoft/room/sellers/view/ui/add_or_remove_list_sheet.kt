package com.chrrissoft.room.sellers.view.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chrrissoft.room.sellers.db.objects.SellerWithRelationship
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.ui.components.MyModalBottomSheet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AndOrRemoveSellerListSheet(
    added: ResState<Map<String, SellerWithRelationship>>,
    available: ResState<Map<String, SellerWithRelationship>>,
    onRemove: (Map<String, SellerWithRelationship>) -> Unit,
    onAdd: (Map<String, SellerWithRelationship>) -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
) {
    MyModalBottomSheet(
        onDismissRequest = onDismissRequest,
        content = { AndOrRemoveSellerList(added, available, onRemove, onAdd) },
        modifier = modifier
    )
}
