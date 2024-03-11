package com.chrrissoft.room.cities.view.ui

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
import com.chrrissoft.room.carriers.view.ui.AndOrRemoveCarrierListSheet
import com.chrrissoft.room.cities.db.objects.CityWithNestedRelationship
import com.chrrissoft.room.costumers.db.objects.CostumerWithRelationship
import com.chrrissoft.room.costumers.view.ui.AndOrRemoveCostumerListSheet
import com.chrrissoft.room.countries.db.objects.CountryWithRelationship
import com.chrrissoft.room.countries.view.ui.CountriesListSheet
import com.chrrissoft.room.orders.db.objects.OrderWithRelationship
import com.chrrissoft.room.orders.view.ui.AndOrRemoveOrderListSheet
import com.chrrissoft.room.sellers.db.objects.SellerWithRelationship
import com.chrrissoft.room.sellers.view.ui.AndOrRemoveSellerListSheet
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.app.ResState.Success
import com.chrrissoft.room.shared.view.ResState
import com.chrrissoft.room.shipments.db.objects.ShippingWithRelationship
import com.chrrissoft.room.shipments.view.ui.AndOrRemoveShippingListSheet
import com.chrrissoft.room.suppliers.db.objects.SupplierWithRelationship
import com.chrrissoft.room.suppliers.view.ui.AndOrRemoveSupplierListSheet
import com.chrrissoft.room.ui.components.RoomDivider
import com.chrrissoft.room.ui.components.SelectableRoomTextField
import com.chrrissoft.room.utils.PairUtils.mapSecond
import com.chrrissoft.room.utils.ResStateUtils.map
import com.chrrissoft.room.utils.Utils
import com.chrrissoft.room.utils.Utils.count

@Composable
fun CityWithRelationship(
    state: ResState<Pair<String, CityWithNestedRelationship>>,
    onStateChange: (Pair<String, CityWithNestedRelationship>) -> Unit,
    countries: ResState<Map<String, CountryWithRelationship>>,
    carriers: ResState<Map<String, CarrierWithRelationship>>,
    onRemoveCarriers: ((Map<String, CarrierWithRelationship>) -> Unit)?,
    onAddCarriers: (Map<String, CarrierWithRelationship>) -> Unit,
    costumers: ResState<Map<String, CostumerWithRelationship>>,
    onRemoveCostumers: (Map<String, CostumerWithRelationship>) -> Unit,
    onAddCostumers: (Map<String, CostumerWithRelationship>) -> Unit,
    orders: ResState<Map<String, OrderWithRelationship>>,
    onRemoveOrders: ((Map<String, OrderWithRelationship>) -> Unit)?,
    onAddOrders: (Map<String, OrderWithRelationship>) -> Unit,
    sellers: ResState<Map<String, SellerWithRelationship>>,
    onRemoveSellers: ((Map<String, SellerWithRelationship>) -> Unit)?,
    onAddSellers: (Map<String, SellerWithRelationship>) -> Unit,
    shipments: ResState<Map<String, ShippingWithRelationship>>,
    onRemoveShipments: ((Map<String, ShippingWithRelationship>) -> Unit)?,
    onAddShipments: (Map<String, ShippingWithRelationship>) -> Unit,
    suppliers: ResState<Map<String, SupplierWithRelationship>>,
    onRemoveSuppliers: ((Map<String, SupplierWithRelationship>) -> Unit)?,
    onAddSuppliers: (Map<String, SupplierWithRelationship>) -> Unit,
    modifier: Modifier = Modifier,
) {
    ResState(state = state) { pair ->
        val data = remember(pair.second) { pair.second }

        var showCountries by remember { mutableStateOf(value = false) }

        if (showCountries) {
            CountriesListSheet(
                state = countries,
                onSelect = {
                    val city = data.city.copy(countryId = it.first)
                    pair.mapSecond { copy(city = city, country = it.second.country) }
                        .let(onStateChange)
                },
                selected = setOf(data.country.id),
                onDismissRequest = { showCountries = false },
            )
        }


        var showCarriers by remember { mutableStateOf(value = false) }

        if (showCarriers) {
            var availableCarriers by remember(carriers) { mutableStateOf(carriers) }
            LaunchedEffect(data.carriers) {
                carriers.map { map -> map.filterNot { data.carriers.contains(it.value) } }
                    .let { availableCarriers = it }
            }
            AndOrRemoveCarrierListSheet(
                added = Success(data.carriers.associateBy { it.carrier.id }),
                available = availableCarriers,
                onRemove = onRemoveCarriers,
                onAdd = onAddCarriers,
                onDismissRequest = { showCarriers = false }
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


        var showSellers by remember { mutableStateOf(value = false) }

        if (showSellers) {
            var availableSeller by remember(sellers) { mutableStateOf(sellers) }
            LaunchedEffect(data.sellers) {
                sellers.map { map -> map.filterNot { data.sellers.contains(it.value) } }
                    .let { availableSeller = it }
            }
            AndOrRemoveSellerListSheet(
                added = Success(data.sellers.associateBy { it.seller.id }),
                available = availableSeller,
                onRemove = onRemoveSellers,
                onAdd = onAddSellers,
                onDismissRequest = { showSellers = false }
            )
        }


        var showShipments by remember { mutableStateOf(value = false) }

        if (showShipments) {
            var availableShipments by remember(shipments) { mutableStateOf(shipments) }
            LaunchedEffect(data.shipments) {
                shipments.map { map -> map.filterNot { data.shipments.contains(it.value) } }
                    .let { availableShipments = it }
            }
            AndOrRemoveShippingListSheet(
                added = Success(data.shipments.associateBy { it.shipping.id }),
                available = availableShipments,
                onRemove = onRemoveShipments,
                onAdd = onAddShipments,
                onDismissRequest = { showShipments = false }
            )
        }


        var showSuppliers by remember { mutableStateOf(value = false) }

        if (showSuppliers) {
            AndOrRemoveSupplierListSheet(
                added = Success(data.suppliers.associateBy { Utils.uuid }),
                available = suppliers,
                onRemove = onRemoveSuppliers,
                onAdd = onAddSuppliers,
                onDismissRequest = { showSuppliers = false }
            )
        }


        Column(modifier.verticalScroll(rememberScrollState())) {
            City(
                state = data.city,
                onStateChange = { onStateChange(pair.mapSecond { copy(city = it) }) }
            )
            RoomDivider()
            SelectableRoomTextField(
                label = "Country",
                selected = showCountries,
                value = data.country.name,
                onClick = { showCountries = true },
            )
            SelectableRoomTextField(
                label = "Carriers",
                selected = showCarriers,
                value = data.carriers.joinToString(limit = 3) { it.carrier.name.first },
                onClick = { showCarriers = true },
            )
            SelectableRoomTextField(
                label = "Costumers",
                selected = showCostumers,
                value = data.costumers.joinToString(limit = 3) { it.costumer.name.first },
                onClick = { showCostumers = true },
            )
            SelectableRoomTextField(
                label = "Orders",
                selected = showOrders,
                value = data.orders.joinToString(limit = 3) { it.order.name },
                onClick = { showOrders = true },
            )
            SelectableRoomTextField(
                label = "Sellers",
                selected = showSellers,
                value = data.sellers.joinToString(limit = 3) { it.seller.name.first },
                onClick = { showSellers = true },
            )
            SelectableRoomTextField(
                label = "Shipments",
                selected = showShipments,
                value = data.shipments.joinToString(limit = 3) { it.shipping.name },
                onClick = { showShipments = true },
            )
            SelectableRoomTextField(
                label = "Suppliers",
                selected = showSuppliers,
                value = data.suppliers.groupBy { it.supplier.id }.toList().joinToString(limit = 3) {
                    it.second.first().supplier.name + it.second.count
                },
                onClick = { showSuppliers = true },
            )
        }
    }
}
