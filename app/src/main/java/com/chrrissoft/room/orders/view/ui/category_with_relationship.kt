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
import com.chrrissoft.room.costumers.db.objects.CostumerWithRelationship
import com.chrrissoft.room.costumers.view.ui.CostumerListSheet
import com.chrrissoft.room.orders.db.objects.OrderWithRelationship
import com.chrrissoft.room.sellers.db.objects.SellerWithRelationship
import com.chrrissoft.room.sellers.view.ui.SellerListSheet
import com.chrrissoft.room.shipments.db.objects.ShippingWithRelationship
import com.chrrissoft.room.shipments.view.ui.ShippingListSheet
import com.chrrissoft.room.ui.components.SelectableRoomTextField

@Composable
fun OrderWithRelationship(
    state: OrderWithRelationship,
    onStateChange: (OrderWithRelationship) -> Unit,
    cities: List<CityWithRelationship>,
    sellers: List<SellerWithRelationship>,
    costumers: List<CostumerWithRelationship>,
    shipments: List<ShippingWithRelationship>,
    modifier: Modifier = Modifier,
) {
    var showCities by remember { mutableStateOf(value = false) }

    if (showCities) {
        CitiesListSheet(
            state = cities,
            selected = setOf(state.city.id),
            onSelect = { onStateChange(state.copy(city = it.city)) },
            onDismissRequest = { showCities = false })
    }


    var showSellers by remember { mutableStateOf(value = false) }

    if (showSellers) {
        SellerListSheet(
            state = sellers,
            selected = setOf(state.seller.id),
            onSelect = { onStateChange(state.copy(seller = it.seller)) },
            onDismissRequest = { showSellers = false })
    }


    var showCostumers by remember { mutableStateOf(value = false) }

    if (showCostumers) {
        CostumerListSheet(
            state = costumers,
            selected = setOf(state.costumer.id),
            onSelect = { onStateChange(state.copy(costumer = it.costumer)) },
            onDismissRequest = { showCostumers = false })
    }

    var showShipments by remember { mutableStateOf(value = false) }

    if (showShipments) {
        ShippingListSheet(
            state = shipments,
            selected = setOf(state.costumer.id),
            onSelect = { onStateChange(state.copy(shipping = it.shipping)) },
            onDismissRequest = { showShipments = false })
    }


    Column(modifier) {
        Order(
            state = state.order,
            onStateChange = { onStateChange(state.copy(order = it)) })

        SelectableRoomTextField(value = state.city.name, onClick = {})
        SelectableRoomTextField(value = state.seller.name.firstName, onClick = {})
        SelectableRoomTextField(value = state.costumer.name.firstName, onClick = {})
        SelectableRoomTextField(value = state.shipping.state.name, onClick = {})
    }
}
