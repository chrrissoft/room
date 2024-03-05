package com.chrrissoft.room.categories.view.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.chrrissoft.room.categories.db.objects.CategoryWithRelationship
import com.chrrissoft.room.orders.db.objects.OrderWithRelationship
import com.chrrissoft.room.orders.view.ui.OrderListSheet
import com.chrrissoft.room.promotions.db.objects.PromotionWithRelationship
import com.chrrissoft.room.promotions.view.ui.PromotionListSheet
import com.chrrissoft.room.sales.db.objects.SaleWithRelationship
import com.chrrissoft.room.sales.view.ui.SaleListSheet
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.view.ResState
import com.chrrissoft.room.ui.components.RoomDivider
import com.chrrissoft.room.ui.components.SelectableRoomTextField

@Composable
fun CategoryWithRelationship(
    state: ResState<Pair<String, CategoryWithRelationship>>,
    onStateChange: (Pair<String, CategoryWithRelationship>) -> Unit,
    orders: ResState<Map<String, OrderWithRelationship>>,
    promotions: ResState<Map<String, PromotionWithRelationship>>,
    sales: ResState<Map<String, SaleWithRelationship>>,
    modifier: Modifier = Modifier,
) {
    ResState(state = state) { res ->
        val data = remember(res) { res.second }

        var showOrders by remember { mutableStateOf(value = false) }

        if (showOrders) {
            val selected = remember(data.orders) { data.orders.mapTo(mutableSetOf()) { it.id } }
            OrderListSheet(
                state = orders,
                onSelect = { order ->
                    (if (data.orders.contains(order.second.order)) data.orders.minus(order.second.order)
                    else data.orders.plus(order.second.order))
                        .let { onStateChange(res.first to data.copy(orders = it)) }
                },
                selected = selected,
                onDismissRequest = { showOrders = false })
        }


        var showPromotions by remember { mutableStateOf(value = false) }

        if (showPromotions) {
            val selected = remember(data.orders) { data.orders.mapTo(mutableSetOf()) { it.id } }
            PromotionListSheet(
                state = promotions,
                onSelect = { promotion ->
                    (if (data.promotions.contains(promotion.second.promotion))
                        data.promotions.minus(promotion.second.promotion)
                    else data.promotions.plus(promotion.second.promotion))
                        .let { onStateChange(res.first to data.copy(promotions = it)) }
                },
                selected = selected,
                onDelete = {},
                onDismissRequest = { showPromotions = false })
        }


        var showSales by remember { mutableStateOf(value = false) }

        if (showSales) {
            val selected = remember(data.sales) { data.sales.mapTo(mutableSetOf()) { it.id } }
            SaleListSheet(
                state = sales,
                onSelect = { sale ->
                    (if (data.sales.contains(sale.second.sale)) data.sales.minus(sale.second.sale)
                    else data.sales.plus(sale.second.sale))
                        .let { onStateChange(res.first to data.copy(sales = it)) }
                },
                selected = selected,
                onDelete = {},
                onDismissRequest = { showSales = false })
        }


        Column(modifier) {
            Category(
                state = data.category,
                onStateChange = { onStateChange(res.first to data.copy(category = it)) })
            RoomDivider()
            SelectableRoomTextField(
                label = "Orders",
                selected = showOrders,
                onClick = { showOrders = true },
                value = data.orders.joinToString(limit = 3) { it.id },
            )
            SelectableRoomTextField(
                label = "Promotions",
                selected = showPromotions,
                onClick = { showPromotions = true },
                value = data.promotions.joinToString(limit = 3) { it.name },
            )
            SelectableRoomTextField(
                label = "Sales",
                selected = showSales,
                onClick = { showSales = true },
                value = data.sales.joinToString(limit = 3) { it.payment.name },
            )
        }
    }
}
