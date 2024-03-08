package com.chrrissoft.room.promotions.view.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chrrissoft.room.promotions.db.objects.PromotionWithRelationship
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.ui.components.MyModalBottomSheet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AndOrRemovePromotionListSheet(
    added: ResState<Map<String, PromotionWithRelationship>>,
    available: ResState<Map<String, PromotionWithRelationship>>,
    onRemove: (Map<String, PromotionWithRelationship>) -> Unit,
    onAdd: (Map<String, PromotionWithRelationship>) -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
) {
    MyModalBottomSheet(
        onDismissRequest = onDismissRequest,
        content = { AndOrRemovePromotionList(added, available, onRemove, onAdd) },
        modifier = modifier
    )
}
