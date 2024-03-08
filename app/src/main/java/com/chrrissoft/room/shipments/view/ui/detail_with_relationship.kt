package com.chrrissoft.room.shipments.view.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.chrrissoft.room.carriers.db.objects.CarrierWithRelationship
import com.chrrissoft.room.carriers.view.ui.CarrierListSheet
import com.chrrissoft.room.orders.db.objects.OrderWithRelationship
import com.chrrissoft.room.orders.view.ui.AndOrRemoveOrderListSheet
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.app.ResState.Success
import com.chrrissoft.room.shared.view.ResState
import com.chrrissoft.room.shipments.db.objects.ShippingWithNestedRelationship
import com.chrrissoft.room.ui.components.RoomDivider
import com.chrrissoft.room.ui.components.SelectableRoomTextField
import com.chrrissoft.room.utils.PairUtils.mapSecond
import com.chrrissoft.room.utils.ResStateUtils.map

@Composable
fun ShippingWithRelationship(
    state: ResState<Pair<String, ShippingWithNestedRelationship>>,
    onStateChange: (Pair<String, ShippingWithNestedRelationship>) -> Unit,
    carriers: ResState<Map<String, CarrierWithRelationship>>,
    orders: ResState<Map<String, OrderWithRelationship>>,
    onRemoveOrders: (Map<String, OrderWithRelationship>) -> Unit,
    onAddOrders: (Map<String, OrderWithRelationship>) -> Unit,
    modifier: Modifier = Modifier,
) {
    ResState(state = state) { pair ->

        val data by remember(pair.second) { mutableStateOf(pair.second) }

        var showCarriers by remember { mutableStateOf(value = false) }

        if (showCarriers) {
            CarrierListSheet(
                state = carriers,
                selected = setOf(data.carrier?.carrier?.id),
                onSelect = {
                    val shipping = data.shipping.copy(carrierId = it.first)
                    pair.mapSecond { copy(shipping = shipping, carrier = it.second) }
                        .let(onStateChange)
                },
                onDismissRequest = { showCarriers = false })
        }


        var showOrders by remember { mutableStateOf(value = false) }

        if (showOrders) {
            var availableOrders by remember(orders) { mutableStateOf(orders) }
            LaunchedEffect(data.orders) {
                orders.map { map -> map.filterNot { data.orders.contains(it.value) } }
                    .let { availableOrders = it }
            }

            AndOrRemoveOrderListSheet(
                added = Success(data.orders.associateBy { it.order.id }),
                available = availableOrders,
                onRemove = onRemoveOrders,
                onAdd = onAddOrders,
                onDismissRequest = { showOrders = false }
            )
        }


        Column(modifier) {
            Shipping(
                state = data.shipping,
                onStateChange = { onStateChange(pair.mapSecond { copy(shipping = it)}) }
            )
            RoomDivider()
            SelectableRoomTextField(
                label = "Carrier",
                value = data.carrier?.carrier?.name?.first ?: "No Carrier",
                selected = showCarriers,
                onClick = { showCarriers = true }
            )
            SelectableRoomTextField(
                selected = showOrders,
                label = "Orders",
                onClick = { showOrders = true },
                value = data.orders.joinToString { "," },
            )
        }
    }
}
