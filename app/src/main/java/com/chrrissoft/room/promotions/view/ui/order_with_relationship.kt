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
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.view.ResState
import com.chrrissoft.room.ui.components.RoomDivider
import com.chrrissoft.room.ui.components.SelectableRoomTextField
import com.chrrissoft.room.utils.PairUtils.mapSecond

@Composable
fun PromotionWithRelationship(
    state: ResState<Pair<String, PromotionWithRelationship>>,
    onStateChange: (Pair<String, PromotionWithRelationship>) -> Unit,
    sales: ResState<Map<String, SaleWithRelationship>>,
    modifier: Modifier = Modifier,
) {
    ResState(state = state) { data ->

        var showSales by remember { mutableStateOf(value = false) }

        if (showSales) {
            val selected = remember(data.second.sales) {
                data.second.sales.mapTo(mutableSetOf()) { it.id }
            }
            SaleListSheet(
                state = sales,
                selected = selected,
                onSelect = { sale ->
                    (if (data.second.sales.contains(sale.second.sale)) data.second.sales.minus(
                        sale.second.sale
                    )
                    else data.second.sales.plus(sale.second.sale)).let {
                        onStateChange(data.mapSecond {
                            copy(
                                sales = it
                            )
                        })
                    }
                },
                onDelete = {},
                onDismissRequest = { showSales = false })
        }


        Column(modifier) {
            Promotion(
                state = data.second.promotion,
                onStateChange = { onStateChange(data.mapSecond { copy(promotion = it) }) })
            RoomDivider()
            SelectableRoomTextField(
                onClick = { showSales = true },
                value = data.second.sales.joinToString { "," },
            )
        }
    }
}
