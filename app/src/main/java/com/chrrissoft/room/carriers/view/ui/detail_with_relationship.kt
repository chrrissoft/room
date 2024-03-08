package com.chrrissoft.room.carriers.view.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.chrrissoft.room.carriers.db.objects.CarrierWithNestedRelationship
import com.chrrissoft.room.cities.db.objects.CityWithRelationship
import com.chrrissoft.room.cities.view.ui.CitiesListSheet
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.app.ResState.Success
import com.chrrissoft.room.shared.view.ResState
import com.chrrissoft.room.shipments.db.objects.ShippingWithRelationship
import com.chrrissoft.room.shipments.view.ui.AndOrRemoveShippingListSheet
import com.chrrissoft.room.ui.components.RoomDivider
import com.chrrissoft.room.ui.components.SelectableRoomTextField
import com.chrrissoft.room.utils.PairUtils.mapSecond
import com.chrrissoft.room.utils.ResStateUtils.map

@Composable
fun CarrierWithRelationship(
    state: ResState<Pair<String, CarrierWithNestedRelationship>>,
    onStateChange: (Pair<String, CarrierWithNestedRelationship>) -> Unit,
    cities: ResState<Map<String, CityWithRelationship>>,
    shipments: ResState<Map<String, ShippingWithRelationship>>,
    onRemoveShipments: (Map<String, ShippingWithRelationship>) -> Unit,
    onAddShipments: (Map<String, ShippingWithRelationship>) -> Unit,
    modifier: Modifier = Modifier,
) {
    var showCities by remember { mutableStateOf(value = false) }

    ResState(state = state) { success ->
        val data = remember(success.second) { success.second }

        if (showCities) {
            CitiesListSheet(
                state = cities,
                selected = setOf(success.second.city.city.id),
                onSelect = {
                    val carrier = success.second.carrier.copy(cityId = it.first)
                    success.mapSecond { copy(carrier = carrier, city = it.second) }
                        .let(onStateChange)
                },
                onDismissRequest = { showCities = false },
            )
        }

        var showShipments by remember { mutableStateOf(value = false) }

        if (showShipments) {
            val added = data.shipments.associateBy { it.shipping.id }
            var availableShipments by
            remember(shipments) { mutableStateOf(shipments) }
            LaunchedEffect(data.shipments) {
                availableShipments
                    .map { res -> res.filterNot { data.shipments.contains(it.value) } }
                    .let { availableShipments = it }
            }

            AndOrRemoveShippingListSheet(
                added = Success(added),
                available = availableShipments,
                onRemove = onRemoveShipments,
                onAdd = onAddShipments,
                onDismissRequest = { showShipments = false }
            )
        }


        Column(modifier) {
            Carrier(
                state = success.second.carrier,
                onStateChange = { change -> onStateChange(success.mapSecond { copy(carrier = change) }) }
            )
            RoomDivider()
            SelectableRoomTextField(
                label = "City",
                value = success.second.city.city.name,
                selected = showCities,
                onClick = { showCities = true },
            )
            SelectableRoomTextField(
                label = "Shipments",
                selected = showShipments,
                onClick = { showShipments = true },
                value = success.second.shipments.joinToString(limit = 3) { it.shipping.state.name },
            )
        }
    }
}
