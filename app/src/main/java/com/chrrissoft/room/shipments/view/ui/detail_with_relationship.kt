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
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.view.ResState
import com.chrrissoft.room.shipments.db.objects.ShippingWithRelationship
import com.chrrissoft.room.ui.components.RoomDivider
import com.chrrissoft.room.ui.components.SelectableRoomTextField
import com.chrrissoft.room.utils.PairUtils.mapSecond

@Composable
fun ShippingWithRelationship(
    state: ResState<Pair<String, ShippingWithRelationship>>,
    onStateChange: (Pair<String, ShippingWithRelationship>) -> Unit,
    carriers: ResState<Map<String, CarrierWithRelationship>>,
    orders: ResState<Map<String, OrderWithRelationship>>,
    modifier: Modifier = Modifier,
) {
    ResState(state = state) { data ->
        var showCarriers by remember { mutableStateOf(value = false) }

        if (showCarriers) {
            CarrierListSheet(
                state = carriers,
                selected = setOf(data.second.shipping.id),
                onSelect = {
                    val shipping = data.second.shipping.copy(carrierId = it.first)
                    data.mapSecond { copy(shipping = shipping, carrier = it.second.carrier) }
                        .let(onStateChange)
                },
                onDismissRequest = { showCarriers = false })
        }


        var showOrders by remember { mutableStateOf(value = false) }

        if (showOrders) {
            val selected = remember(data.second.orders) { data.second.orders.mapTo(mutableSetOf()) { it.id } }
            OrderListSheet(
                state = orders,
                onSelect = { order ->
                    (if (data.second.orders.contains(order.second.order)) data.second.orders.minus(order.second.order)
                    else data.second.orders.plus(order.second.order)).let {
                        onStateChange(data.mapSecond { copy(orders = it)})
                    }
                },
                selected = selected,
                onDismissRequest = { showOrders = false })
        }


        Column(modifier) {
            Shipping(
                state = data.second.shipping,
                onStateChange = { onStateChange(data.mapSecond { copy(shipping = it)}) }
            )
            RoomDivider()
            SelectableRoomTextField(
                label = "Carriers",
                value = data.second.carrier.name.firstName,
                selected = showCarriers,
                onClick = { showCarriers = true }
            )
            SelectableRoomTextField(
                selected = showOrders,
                label = "Orders",
                onClick = { showOrders = true },
                value = data.second.orders.joinToString { "," },
            )
        }
    }
}
