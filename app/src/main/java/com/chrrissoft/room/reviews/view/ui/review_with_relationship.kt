package com.chrrissoft.room.reviews.view.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.chrrissoft.room.costumers.db.objects.CostumerWithRelationship
import com.chrrissoft.room.costumers.view.ui.CostumerListSheet
import com.chrrissoft.room.products.db.objects.ProductWithRelationship
import com.chrrissoft.room.products.view.ui.ProductListSheet
import com.chrrissoft.room.reviews.db.objects.ReviewWithRelationship
import com.chrrissoft.room.ui.components.SelectableRoomTextField

@Composable
fun ReviewWithRelationship(
    state: ReviewWithRelationship,
    onStateChange: (ReviewWithRelationship) -> Unit,
    costumers: List<CostumerWithRelationship>,
    products: List<ProductWithRelationship>,
    modifier: Modifier = Modifier,
) {
    var showCostumers by remember { mutableStateOf(value = false) }

    if (showCostumers) {
        CostumerListSheet(
            state = costumers,
            selected = setOf(state.costumer.id),
            onSelect = { onStateChange(state.copy(costumer = it.costumer)) },
            onDismissRequest = { showCostumers = false })
    }


    var showProducts by remember { mutableStateOf(value = false) }

    if (showProducts) {
        ProductListSheet(
            state = products,
            selected = setOf(state.product.id),
            onSelect = { onStateChange(state.copy(product = it.product)) },
            onDismissRequest = { showProducts = false })
    }

    Column(modifier) {
        Review(state = state.review, onStateChange = { onStateChange(state.copy(review = it)) })
        SelectableRoomTextField(value = state.product.name) { showProducts = true }
        SelectableRoomTextField(value = state.costumer.name.firstName) { showCostumers = true }
    }
}
