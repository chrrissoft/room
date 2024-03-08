package com.chrrissoft.room.orders.view.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.chrrissoft.room.cities.db.objects.CityWithRelationship
import com.chrrissoft.room.cities.view.ui.CitiesListSheet
import com.chrrissoft.room.orders.db.objects.OrderWithNestedRelationship
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.view.ResState
import com.chrrissoft.room.shipments.db.objects.ShippingWithRelationship
import com.chrrissoft.room.shipments.view.ui.AndOrRemoveShippingListSheet
import com.chrrissoft.room.shipments.view.ui.ShippingListSheet
import com.chrrissoft.room.ui.components.RoomDivider
import com.chrrissoft.room.ui.components.SelectableRoomTextField
import com.chrrissoft.room.utils.PairUtils.mapSecond

@Composable
fun OrderWithRelationship(
    state: ResState<Pair<String, OrderWithNestedRelationship>>,
    onStateChange: (Pair<String, OrderWithNestedRelationship>) -> Unit,
    cities: ResState<Map<String, CityWithRelationship>>,
    shipments : ResState<Map<String, ShippingWithRelationship>>,
    modifier: Modifier = Modifier,
) {
    ResState(state = state) { pair ->
        var showCities by remember { mutableStateOf(value = false) }

        if (showCities) {
            CitiesListSheet(
                state = cities,
                selected = setOf(pair.second.city.city.id),
                onSelect = {
                    val order = pair.second.order.copy(cityId = it.first)
                    pair.mapSecond { copy(order = order, city = it.second) }
                        .let(onStateChange)
                },
                onDismissRequest = { showCities = false })
        }


        var showShipments by remember { mutableStateOf(value = false) }

        if (showShipments) {
            ShippingListSheet(
                state = shipments,
                selected = setOf(pair.second.shipping?.shipping?.id),
                onSelect = {
                    val order = pair.second.order.copy(shippingId = it.first)
                    pair.mapSecond { copy(order = order, shipping = it.second) }
                        .let(onStateChange)
                },
                onDismissRequest = { showShipments = false })
        }


        Column(modifier) {
            Order(
                state = pair.second.order,
                onStateChange = { onStateChange(pair.mapSecond { copy(order = it) }) })
            RoomDivider()
            SelectableRoomTextField(
                label = "City",
                value = pair.second.city.city.name,
                selected = showCities,
                onClick = { showCities = true }
            )
            SelectableRoomTextField(
                label = "Shipment",
                value = pair.second.shipping?.shipping?.state?.name ?: "No Shipment",
                selected = showShipments,
                onClick = { showShipments = true }
            )
        }
    }
}
