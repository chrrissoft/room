package com.chrrissoft.room.products.view.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.chrrissoft.room.products.db.objects.ProductWithNestedRelationship
import com.chrrissoft.room.promotions.db.objects.PromotionWithRelationship
import com.chrrissoft.room.promotions.view.ui.PromotionListSheet
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.view.ResState
import com.chrrissoft.room.ui.components.RoomDivider
import com.chrrissoft.room.ui.components.SelectableRoomTextField
import com.chrrissoft.room.utils.PairUtils.mapSecond

@Composable
fun ProductWithRelationship(
    state: ResState<Pair<String, ProductWithNestedRelationship>>,
    onStateChange: (Pair<String, ProductWithNestedRelationship>) -> Unit,
    promotions: ResState<Map<String, PromotionWithRelationship>>,
    modifier: Modifier = Modifier,
) {
    ResState(state = state) { pair ->

        var showPromotions by remember { mutableStateOf(value = false) }

        if (showPromotions) {
            PromotionListSheet(
                state = promotions,
                selected = setOf(pair.second.promotion?.promotion?.id),
                onSelect = {
                    val order = pair.second.product.copy(promotionId = it.first)
                    pair.mapSecond { copy(product = order, promotion = it.second) }
                        .let(onStateChange)
                },
                onDismissRequest = { showPromotions = false })
        }


        Column(modifier) {
            Product(
                state = pair.second.product,
                onStateChange = { onStateChange(pair.mapSecond { copy(product = it)}) }
            )
            RoomDivider()
            SelectableRoomTextField(
                label = "Promotion",
                selected = showPromotions,
                value = pair.second.promotion?.promotion?.name ?: "No promotions",
                onClick = { showPromotions = true },
            )
        }
    }
}
