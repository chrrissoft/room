package com.chrrissoft.room.products.view.ui

import androidx.compose.runtime.Composable
import com.chrrissoft.room.products.db.objects.ProductWithRelationship

@Composable
fun ProductListSheet(
    state: List<ProductWithRelationship>,
    selected: Set<String>,
    onSelect: (ProductWithRelationship) -> Unit,
    onDismissRequest: () -> Unit
) {

}