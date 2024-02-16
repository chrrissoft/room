package com.chrrissoft.room.sales.view.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chrrissoft.room.sales.db.objects.SaleWithRelationship
import com.chrrissoft.room.ui.components.MyModalBottomSheet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SaleListSheet(
    state: List<SaleWithRelationship>,
    onSelect: (SaleWithRelationship) -> Unit,
    selected: Set<String>,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
) {
    MyModalBottomSheet(
        onDismissRequest = onDismissRequest,
        content = { SaleList(state = state, onSelect = onSelect, selected = selected) },
        modifier = modifier
    )
}
