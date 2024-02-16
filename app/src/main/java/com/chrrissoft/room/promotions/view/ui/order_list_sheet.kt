package com.chrrissoft.room.promotions.view.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chrrissoft.room.promotions.db.objects.PromotionWithRelationship
import com.chrrissoft.room.ui.components.MyModalBottomSheet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PromotionListSheet(
    state: List<PromotionWithRelationship>,
    onSelect: (PromotionWithRelationship) -> Unit,
    selected: Set<String>,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
) {
    MyModalBottomSheet(
        onDismissRequest = onDismissRequest,
        content = { PromotionList(state = state, onSelect = onSelect, selected = selected) },
        modifier = modifier
    )
}
