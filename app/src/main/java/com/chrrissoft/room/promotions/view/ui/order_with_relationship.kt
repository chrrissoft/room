package com.chrrissoft.room.promotions.view.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.chrrissoft.room.promotions.db.objects.PromotionWithRelationship
import com.chrrissoft.room.sales.db.objects.SaleWithRelationship
import com.chrrissoft.room.sales.view.ui.SaleListSheet
import com.chrrissoft.room.ui.components.SelectableRoomTextField

@Composable
fun PromotionWithRelationship(
    state: PromotionWithRelationship,
    onStateChange: (PromotionWithRelationship) -> Unit,
    sellers: List<SaleWithRelationship>,
    modifier: Modifier = Modifier,
) {
    var showSales by remember { mutableStateOf(value = false) }

    if (showSales) {
        val selected = remember(state.sales) {
            state.sales.mapTo(mutableSetOf()) { it.id }
        }
        SaleListSheet(
            state = sellers,
            selected = selected,
            onSelect = { sale ->
                (if (state.sales.contains(sale.sale)) state.sales.minus(sale.sale)
                else state.sales.plus(sale.sale)).let { onStateChange(state.copy(sales = it)) }
            },
            onDismissRequest = { showSales = false })
    }


    Column(modifier) {
        Promotion(
            state = state.promotion,
            onStateChange = { onStateChange(state.copy(promotion = it)) })
        SelectableRoomTextField(value = state.sales.joinToString { "," }) { showSales = true }
    }
}
