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
import com.chrrissoft.room.ui.components.SelectableRoomTextField

@Composable
fun CategoryWithRelationship(
    state: CategoryWithRelationship,
    onStateChange: (CategoryWithRelationship) -> Unit,
    orders: List<OrderWithRelationship>,
    promotions: List<PromotionWithRelationship>,
    sales: List<SaleWithRelationship>,
    modifier: Modifier = Modifier,
) {
    var showOrders by remember { mutableStateOf(value = false) }

    if (showOrders) {
        val selected = remember(state.orders) { state.orders.mapTo(mutableSetOf()) { it.id } }
        OrderListSheet(
            state = orders,
            onSelect = { order ->
                (if (state.orders.contains(order.order)) state.orders.minus(order.order)
                else state.orders.plus(order.order)).let { onStateChange(state.copy(orders = it)) }
            },
            selected = selected,
            onDismissRequest = { showOrders = false })
    }


    var showPromotions by remember { mutableStateOf(value = false) }

    if (showPromotions) {
        val selected = remember(state.orders) { state.orders.mapTo(mutableSetOf()) { it.id } }
        PromotionListSheet(
            state = promotions,
            onSelect = { promotion ->
                (if (state.promotions.contains(promotion.promotion)) state.promotions.minus(promotion.promotion)
                else state.promotions.plus(promotion.promotion)).let { onStateChange(state.copy(promotions = it)) }
            },
            selected = selected,
            onDismissRequest = { showPromotions = false })
    }


    var showSales by remember { mutableStateOf(value = false) }

    if (showSales) {
        val selected = remember(state.sales) { state.sales.mapTo(mutableSetOf()) { it.id } }
        SaleListSheet(
            state = sales,
            onSelect = { sale ->
                (if (state.sales.contains(sale.sale)) state.sales.minus(sale.sale)
                else state.sales.plus(sale.sale)).let { onStateChange(state.copy(sales = it)) }
            },
            selected = selected,
            onDismissRequest = { showSales = false })
    }


    Column(modifier) {
        Category(
            state = state.category,
            onStateChange = { onStateChange(state.copy(category = it)) })

        SelectableRoomTextField(value = state.orders.joinToString { ", " }, onClick = {})
        SelectableRoomTextField(value = state.promotions.joinToString { ", " }, onClick = {})
        SelectableRoomTextField(value = state.sales.joinToString { ", " }, onClick = {})
    }
}
