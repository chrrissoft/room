package com.chrrissoft.room.shipments.view.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.chrrissoft.room.carriers.db.objects.CarrierWithRelationship
import com.chrrissoft.room.carriers.view.ui.CarrierListSheet
import com.chrrissoft.room.cities.db.objects.CityWithRelationship
import com.chrrissoft.room.cities.view.ui.AndOrRemoveCityListSheet
import com.chrrissoft.room.costumers.db.objects.CostumerWithRelationship
import com.chrrissoft.room.costumers.view.ui.AndOrRemoveCostumerListSheet
import com.chrrissoft.room.orders.db.objects.OrderWithRelationship
import com.chrrissoft.room.orders.view.ui.AndOrRemoveOrderListSheet
import com.chrrissoft.room.sellers.db.objects.SellerWithRelationship
import com.chrrissoft.room.sellers.view.ui.AndOrRemoveSellerListSheet
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
    cities: ResState<Map<String, CityWithRelationship>>,
    onRemoveCities: (Map<String, CityWithRelationship>) -> Unit,
    onAddCities: (Map<String, CityWithRelationship>) -> Unit,
    costumers: ResState<Map<String, CostumerWithRelationship>>,
    onRemoveCostumers: (Map<String, CostumerWithRelationship>) -> Unit,
    onAddCostumers: (Map<String, CostumerWithRelationship>) -> Unit,
    sellers: ResState<Map<String, SellerWithRelationship>>,
    onRemoveSellers: (Map<String, SellerWithRelationship>) -> Unit,
    onAddSellers: (Map<String, SellerWithRelationship>) -> Unit,
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


        var showCities by remember { mutableStateOf(value = false) }

        if (showCities) {
            var availableCities by remember(cities) { mutableStateOf(cities) }
            LaunchedEffect(data.cities) {
                cities.map { map -> map.filterNot { data.cities.contains(it.value) } }
                    .let { availableCities = it }
            }

            AndOrRemoveCityListSheet(
                added = Success(data.cities.associateBy { it.city.id }),
                available = availableCities,
                onRemove = onRemoveCities,
                onAdd = onAddCities,
                onDismissRequest = { showCities = false }
            )
        }


        var showCostumers by remember { mutableStateOf(value = false) }

        if (showCostumers) {
            var availableCostumers by remember(costumers) { mutableStateOf(costumers) }
            LaunchedEffect(data.costumers) {
                costumers.map { map -> map.filterNot { data.costumers.contains(it.value) } }
                    .let { availableCostumers = it }
            }

            AndOrRemoveCostumerListSheet(
                added = Success(data.costumers.associateBy { it.costumer.id }),
                available = availableCostumers,
                onRemove = onRemoveCostumers,
                onAdd = onAddCostumers,
                onDismissRequest = { showCostumers = false }
            )
        }


        var showSellers by remember { mutableStateOf(value = false) }

        if (showSellers) {
            var availableSellers by remember(sellers) { mutableStateOf(sellers) }
            LaunchedEffect(data.sellers) {
                sellers.map { map -> map.filterNot { data.sellers.contains(it.value) } }
                    .let { availableSellers = it }
            }

            AndOrRemoveSellerListSheet(
                added = Success(data.sellers.associateBy { it.seller.id }),
                available = availableSellers,
                onRemove = onRemoveSellers,
                onAdd = onAddSellers,
                onDismissRequest = { showSellers = false }
            )
        }

        Column(modifier.verticalScroll(rememberScrollState())) {
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
                value = data.orders.joinToString(limit = 3) { it.order.name },
            )
            SelectableRoomTextField(
                label = "Cities",
                selected = showCities,
                onClick = { showCities = true },
                value = data.cities.joinToString(limit = 3) { it.city.name },
            )
            SelectableRoomTextField(
                label = "Costumers",
                selected = showCostumers,
                value = data.costumers.joinToString(limit = 3) { it.costumer.name.first },
                onClick = { showCostumers = true },
            )
            SelectableRoomTextField(
                label = "Sellers",
                selected = showSellers,
                value = data.sellers.joinToString(limit = 3) { it.seller.name.first },
                onClick = { showSellers = true },
            )
        }
    }
}
