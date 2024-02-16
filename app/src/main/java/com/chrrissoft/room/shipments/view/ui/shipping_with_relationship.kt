package com.chrrissoft.room.shipments.view.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.chrrissoft.room.carriers.db.objects.CarrierWithRelationship
import com.chrrissoft.room.carriers.view.ui.CarrierListSheet
import com.chrrissoft.room.orders.db.objects.OrderWithRelationship
import com.chrrissoft.room.orders.view.ui.OrderListSheet
import com.chrrissoft.room.sales.db.objects.SaleWithRelationship
import com.chrrissoft.room.sales.view.ui.SaleListSheet
import com.chrrissoft.room.shipments.db.objects.ShippingWithRelationship
import com.chrrissoft.room.ui.components.SelectableRoomTextField

@Composable
fun ShippingWithRelationship(
    state: ShippingWithRelationship,
    onStateChange: (ShippingWithRelationship) -> Unit,
    carriers: List<CarrierWithRelationship>,
    orders: List<OrderWithRelationship>,
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


    var showCarriers by remember { mutableStateOf(value = false) }

    if (showCarriers) {
        CarrierListSheet(
            state = carriers,
            selected = setOf(state.carrier.id),
            onSelect = { onStateChange(state.copy(carrier = it.carrier)) },
            onDismissRequest = { showCarriers = false })
    }


    Column(modifier) {
        Shipping(
            state = state.shipping,
            onStateChange = { onStateChange(state.copy(shipping = it)) })

        SelectableRoomTextField(value = state.carrier.name.firstName) {  }

        SelectableRoomTextField(value = sales.joinToString { "," }) { showOrders = true }
        SelectableRoomTextField(value = orders.joinToString { "," }) { showOrders = true }
    }
}
