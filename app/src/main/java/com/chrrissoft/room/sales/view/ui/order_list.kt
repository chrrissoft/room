package com.chrrissoft.room.sales.view.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chrrissoft.room.sales.db.objects.SaleWithRelationship

@Composable
fun SaleList(
    state: List<SaleWithRelationship>,
    onSelect: (SaleWithRelationship) -> Unit,
    selected: Set<String>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier) {
        items(state) {

        }
    }
}
