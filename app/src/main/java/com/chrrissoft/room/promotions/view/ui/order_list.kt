package com.chrrissoft.room.promotions.view.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chrrissoft.room.promotions.db.objects.PromotionWithRelationship

@Composable
fun PromotionList(
    state: List<PromotionWithRelationship>,
    onSelect: (PromotionWithRelationship) -> Unit,
    selected: Set<String>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier) {
        items(state) {

        }
    }
}
