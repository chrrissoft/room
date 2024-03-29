package com.chrrissoft.room.promotions.view.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chrrissoft.room.promotions.db.objects.PromotionWithRelationship
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.ui.components.MyModalBottomSheet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PromotionListSheet(
    state: ResState<Map<String, PromotionWithRelationship>>,
    onSelect: (Pair<String, PromotionWithRelationship>) -> Unit,
    selected: Set<String?>,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    onDelete: ((Map<String, PromotionWithRelationship>) -> Unit)? = null,
) {
    MyModalBottomSheet(
        onDismissRequest = onDismissRequest,
        content = { PromotionList(state, onSelect, selected, onDelete = onDelete) },
        modifier = modifier
    )
}
