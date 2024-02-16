package com.chrrissoft.room.orders.view.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chrrissoft.room.orders.db.objects.OrderWithRelationship

@Composable
fun OrderList(
    state: List<OrderWithRelationship>,
    onSelect: (OrderWithRelationship) -> Unit,
    selected: Set<String>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier) {
        items(state) {

        }
    }
}
