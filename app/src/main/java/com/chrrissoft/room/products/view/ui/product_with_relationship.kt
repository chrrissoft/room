package com.chrrissoft.room.products.view.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chrrissoft.room.products.db.objects.ProductWithRelationship

@Composable
fun ProductWithRelationship(
    state: ProductWithRelationship,
    onStateChange: (ProductWithRelationship) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        Product(state = state.product, onStateChange = { onStateChange(state.copy(product = it)) })
    }
}
