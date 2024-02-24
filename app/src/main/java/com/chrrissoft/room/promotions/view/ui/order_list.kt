package com.chrrissoft.room.promotions.view.ui

import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chrrissoft.room.promotions.db.objects.PromotionWithRelationship
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.view.ResStateMap
import com.chrrissoft.room.ui.components.SelectableRoomTextField

@Composable
fun PromotionList(
    state: ResState<Map<String, PromotionWithRelationship>>,
    onSelect: (Pair<String, PromotionWithRelationship>) -> Unit,
    onDelete: (Map<String, PromotionWithRelationship>) -> Unit,
    selected: Set<String>,
    modifier: Modifier = Modifier,
) {
    ResStateMap(state = state, modifier) {
        items(it.toList()) {
            SelectableRoomTextField(value = it.second.promotion.name) {
                onSelect(it)
            }
        }
    }
}
