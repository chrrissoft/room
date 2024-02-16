package com.chrrissoft.room.sellers.view.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.chrrissoft.room.cities.db.objects.CityWithRelationship
import com.chrrissoft.room.cities.view.ui.CitiesListSheet
import com.chrrissoft.room.orders.db.objects.OrderWithRelationship
import com.chrrissoft.room.orders.view.ui.OrderListSheet
import com.chrrissoft.room.sales.db.objects.SaleWithRelationship
import com.chrrissoft.room.sales.view.ui.SaleListSheet
import com.chrrissoft.room.sellers.db.objects.SellerWithRelationship
import com.chrrissoft.room.ui.components.SelectableRoomTextField

@Composable
fun SellerWithRelationship(
    state: SellerWithRelationship,
    onStateChange: (SellerWithRelationship) -> Unit,
    cities: List<CityWithRelationship>,
    sales: List<SaleWithRelationship>,
    orders: List<OrderWithRelationship>,
    modifier: Modifier = Modifier,
) {
    var showCities by remember { mutableStateOf(value = false) }

    if (showCities) {
        CitiesListSheet(
            state = cities,
            selected = setOf(state.city.id),
            onSelect = { onStateChange(state.copy(city = it.city)) },
            onDismissRequest = { showCities = false },
        )
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

    Column(modifier) {
        Seller(state = state.seller, onStateChange = { onStateChange(state.copy(seller = it)) })
        SelectableRoomTextField(value = state.city.name) {  }
        SelectableRoomTextField(value = state.sales.joinToString { "," }) {  }
        SelectableRoomTextField(value = state.orders.joinToString { "," }) {  }
    }
}