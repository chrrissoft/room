package com.chrrissoft.room.sellers.view.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chrrissoft.room.sellers.db.objects.SellerWithRelationship
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.ui.components.MyModalBottomSheet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SellerListSheet(
    state: ResState<Map<String,SellerWithRelationship>>,
    selected: Set<String>,
    onSelect: (Pair<String,SellerWithRelationship>) -> Unit,
    onDelete: (Map<String,SellerWithRelationship>) -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
) {
    MyModalBottomSheet(
        onDismissRequest = onDismissRequest,
        content = { SellersList(state, onSelect, onDelete, selected) },
        modifier = modifier
    )
}
