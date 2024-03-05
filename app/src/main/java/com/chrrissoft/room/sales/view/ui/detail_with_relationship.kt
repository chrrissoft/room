package com.chrrissoft.room.sales.view.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.chrrissoft.room.costumers.db.objects.CostumerWithRelationship
import com.chrrissoft.room.costumers.view.ui.CostumerListSheet
import com.chrrissoft.room.orders.db.objects.OrderWithRelationship
import com.chrrissoft.room.orders.view.ui.OrderListSheet
import com.chrrissoft.room.sales.db.objects.SaleWithRelationship
import com.chrrissoft.room.sellers.db.objects.SellerWithRelationship
import com.chrrissoft.room.sellers.view.ui.SellerListSheet
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.view.ResState
import com.chrrissoft.room.ui.components.RoomDivider
import com.chrrissoft.room.ui.components.SelectableRoomTextField
import com.chrrissoft.room.utils.PairUtils.mapSecond

@Composable
fun SaleWithRelationship(
    state: ResState<Pair<String, SaleWithRelationship>>,
    onStateChange: (Pair<String, SaleWithRelationship>) -> Unit,
    orders: ResState<Map<String, OrderWithRelationship>>,
    sellers: ResState<Map<String, SellerWithRelationship>>,
    costumers: ResState<Map<String, CostumerWithRelationship>>,
    modifier: Modifier = Modifier,
) {
    ResState(state = state) { pair ->
        val data = remember(pair.second) { pair.second }

        var showSellers by remember { mutableStateOf(value = false) }

        if (showSellers) {
            SellerListSheet(
                state = sellers,
                selected = setOf(data.seller.id),
                onSelect = {
                    val sale = data.sale.copy(sellerId = it.first)
                    pair.mapSecond { copy(sale = sale, seller = it.second.seller) }
                        .let(onStateChange)
                },
                onDelete = {},
                onDismissRequest = { showSellers = false })
        }


        var showCostumers by remember { mutableStateOf(value = false) }

        if (showCostumers) {
            CostumerListSheet(
                state = costumers,
                selected = setOf(data.costumer.id),
                onSelect = {
                    val sale = data.sale.copy(costumerId = it.first)
                    pair.mapSecond { copy(sale = sale, costumer = it.second.costumer) }
                        .let(onStateChange)
                },
                onDismissRequest = { showCostumers = false })
        }


        var showOrders by remember { mutableStateOf(value = false) }

        if (showOrders) {
            OrderListSheet(
                state = orders,
                onSelect = {
                    val sale = data.sale.copy(orderId = it.first)
                    pair.mapSecond { copy(sale = sale, order = it.second.order) }
                        .let(onStateChange)
                },
                selected = setOf(data.order.id),
                onDismissRequest = { showOrders = false })
        }

        Column(modifier) {
            Sale(
                state = data.sale,
                onStateChange = { onStateChange(pair.mapSecond { copy(sale = it) }) })
            RoomDivider()
            SelectableRoomTextField(
                label = "Sellers",
                selected = showSellers,
                value = data.seller.name.firstName,
                onClick = { showSellers = true },
            )
            SelectableRoomTextField(
                label = "Costumers",
                selected = showCostumers,
                value = data.costumer.name.firstName,
                onClick = { showCostumers = true },
            )
            SelectableRoomTextField(
                label = "Orders",
                selected = showOrders,
                value = data.order.direction.street,
                onClick = { showOrders = true },
            )
        }
    }
}
