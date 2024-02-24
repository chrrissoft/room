package com.chrrissoft.room.sales.view.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
                onSelect = { onStateChange(pair.mapSecond { copy(seller = it.second.seller)}) },
                onDelete = {},
                onDismissRequest = { showSellers = false })
        }


        var showCostumers by remember { mutableStateOf(value = false) }

        if (showCostumers) {
            CostumerListSheet(
                state = costumers,
                selected = setOf(data.costumer.id),
                onSelect = { onStateChange(pair.mapSecond { copy(costumer = it.second.costumer)}) },
                onDelete = {},
                onDismissRequest = { showCostumers = false })
        }


        var showOrders by remember { mutableStateOf(value = false) }

        if (showOrders) {
            OrderListSheet(
                state = orders,
                onSelect = { onStateChange(pair.mapSecond { copy(order = it.second.order)}) },
                selected = setOf(data.order.id),
                onDelete = {},
                onDismissRequest = { showOrders = false })
        }

        Column(modifier) {
            Sale(state = data.sale, onStateChange = { onStateChange(pair.mapSecond { copy(sale = it)}) })
            RoomDivider()
            SelectableRoomTextField(value = data.seller.name.firstName) { showSellers = true }
            Spacer(modifier = Modifier.height(10.dp))
            SelectableRoomTextField(value = data.costumer.name.firstName) { showCostumers = true }
            Spacer(modifier = Modifier.height(10.dp))
            SelectableRoomTextField(value = data.order.direction.street) { showOrders = true }
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}
