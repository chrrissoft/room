package com.chrrissoft.room.cities.view.ui

import androidx.compose.foundation.layout.Column
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

@Composable
fun CityWithRelationship(
    state: ResState<Pair<String, CityWithNestedRelationship>>,
    onStateChange: (Pair<String, CityWithNestedRelationship>) -> Unit,
    countries: ResState<Map<String, CountryWithRelationship>>,
    carriers: ResState<Map<String, CarrierWithRelationship>>,
    onRemoveCarriers: (Map<String, CarrierWithRelationship>) -> Unit,
    onAddCarriers: (Map<String, CarrierWithRelationship>) -> Unit,
    costumers: ResState<Map<String, CostumerWithRelationship>>,
    onRemoveCostumers: (Map<String, CostumerWithRelationship>) -> Unit,
    onAddCostumers: (Map<String, CostumerWithRelationship>) -> Unit,
    orders: ResState<Map<String, OrderWithRelationship>>,
    onRemoveOrders: (Map<String, OrderWithRelationship>) -> Unit,
    onAddOrders: (Map<String, OrderWithRelationship>) -> Unit,
    sellers: ResState<Map<String, SellerWithRelationship>>,
    onRemoveSellers: (Map<String, SellerWithRelationship>) -> Unit,
    onAddSellers: (Map<String, SellerWithRelationship>) -> Unit,
    shipments: ResState<Map<String, ShippingWithRelationship>>,
    onRemoveShipments: (Map<String, ShippingWithRelationship>) -> Unit,
    onAddShipments: (Map<String, ShippingWithRelationship>) -> Unit,
    suppliers: ResState<Map<String, SupplierWithRelationship>>,
    onRemoveSuppliers: (Map<String, SupplierWithRelationship>) -> Unit,
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
            AndOrRemoveCarrierListSheet(
                added = Success(data.carriers.associateBy { it.carrier.id }),
                available = carriers,
                onRemove = onRemoveCarriers,
                onAdd = onAddCarriers,
                onDismissRequest = { showCarriers = false }
            )
        }


        var showCostumers by remember { mutableStateOf(value = false) }

        if (showCostumers) {
            AndOrRemoveCostumerListSheet(
                added = Success(data.costumers.associateBy { it.costumer.id }),
                available = costumers,
                onRemove = onRemoveCostumers,
                onAdd = onAddCostumers,
                onDismissRequest = { showCostumers = false }
            )
        }

        var showOrders by remember { mutableStateOf(value = false) }

        if (showOrders) {
            AndOrRemoveOrderListSheet(
                added = Success(data.orders.associateBy { it.order.id }),
                available = orders,
                onRemove = onRemoveOrders,
                onAdd = onAddOrders,
                onDismissRequest = { showOrders = false }
            )
        }


        var showSellers by remember { mutableStateOf(value = false) }

        if (showSellers) {
            AndOrRemoveSellerListSheet(
                added = Success(data.sellers.associateBy { it.seller.id }),
                available = sellers,
                onRemove = onRemoveSellers,
                onAdd = onAddSellers,
                onDismissRequest = { showSellers = false }
            )
        }


        var showShipments by remember { mutableStateOf(value = false) }

        if (showShipments) {
            AndOrRemoveShippingListSheet(
                added = Success(data.shipments.associateBy { it.shipping.id }),
                available = shipments,
                onRemove = onRemoveShipments,
                onAdd = onAddShipments,
                onDismissRequest = { showShipments = false }
            )
        }


        var showSuppliers by remember { mutableStateOf(value = false) }

        if (showSuppliers) {
            var availableSuppliers by remember(suppliers) { mutableStateOf(suppliers) }
            LaunchedEffect(data.suppliers) {
                suppliers.map { map -> map.filterNot { data.suppliers.contains(it.value) } }
                    .let { availableSuppliers = it }
            }

            AndOrRemoveSupplierListSheet(
                added = ResState.Success(data.suppliers.associateBy { it.supplier.id }),
                available = availableSuppliers,
                onRemove = onRemoveSuppliers,
                onAdd = onAddSuppliers,
                onDismissRequest = { showSuppliers = false }
            )
        }

        Column(modifier) {
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
        }
    }
}
