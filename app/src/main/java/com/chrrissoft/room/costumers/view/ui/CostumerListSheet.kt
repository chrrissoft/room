package com.chrrissoft.room.costumers.view.ui

import androidx.compose.runtime.Composable
import com.chrrissoft.room.costumers.db.objects.CostumerWithRelationship

@Composable
fun CostumerListSheet(
    state: List<CostumerWithRelationship>,
    selected: Set<String>,
    onSelect: (CostumerWithRelationship) -> Unit,
    onDismissRequest: () -> Unit
) {

}