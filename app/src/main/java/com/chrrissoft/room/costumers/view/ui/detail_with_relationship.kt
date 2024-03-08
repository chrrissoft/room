package com.chrrissoft.room.costumers.view.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.chrrissoft.room.cities.db.objects.CityWithRelationship
import com.chrrissoft.room.cities.view.ui.CitiesListSheet
import com.chrrissoft.room.costumers.db.objects.CostumerWithNestedRelationship
import com.chrrissoft.room.products.db.objects.ProductWithRelationship
import com.chrrissoft.room.products.view.ui.AndOrRemoveProductListSheet
import com.chrrissoft.room.sales.db.objects.SaleWithRelationship
import com.chrrissoft.room.sales.view.ui.AndOrRemoveSaleListSheet
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
fun CostumerWithRelationship(
    state: ResState<Pair<String, CostumerWithNestedRelationship>>,
    onStateChange: (Pair<String, CostumerWithNestedRelationship>) -> Unit,
    cities: ResState<Map<String, CityWithRelationship>>,
    sales: ResState<Map<String, SaleWithRelationship>>,
    onRemoveSales: (Map<String, SaleWithRelationship>) -> Unit,
    onAddSales: (Map<String, SaleWithRelationship>) -> Unit,
    products: ResState<Map<String, ProductWithRelationship>>,
    onRemoveProducts: (Map<String, ProductWithRelationship>) -> Unit,
    onAddProducts: (Map<String, ProductWithRelationship>) -> Unit,
    shipments: ResState<Map<String, ShippingWithRelationship>>,
    onRemoveShipments: (Map<String, ShippingWithRelationship>) -> Unit,
    onAddShipments: (Map<String, ShippingWithRelationship>) -> Unit,
    modifier: Modifier = Modifier,
) {
    ResState(state = state) { pair ->
        val data = remember(pair.second) { pair.second }

        var showCities by remember { mutableStateOf(value = false) }

        if (showCities) {
            CitiesListSheet(
                state = cities,
                selected = setOf(data.city?.city?.id),
                onSelect = { change ->
                    val costumer = pair.second.costumer.copy(cityId = change.first)
                    pair.mapSecond { copy(costumer = costumer, city = change.second) }
                        .let(onStateChange)
                },
                onDismissRequest = { showCities = false },
            )
        }


        var showSales by remember { mutableStateOf(value = false) }

        if (showSales) {
            AndOrRemoveSaleListSheet(
                added = Success(data.sales.associateBy { it.sale.id }),
                available = sales,
                onRemove = onRemoveSales,
                onAdd = onAddSales,
                onDismissRequest = { showSales = false }
            )
        }


        var showProducts by remember { mutableStateOf(value = false) }

        if (showProducts) {
            var availableProducts by remember(products) { mutableStateOf(products) }
            LaunchedEffect(data.products) {
                products.map { map -> map.filterNot { data.products.contains(it.value) } }
                    .let { availableProducts = it }
            }

            AndOrRemoveProductListSheet(
                added = Success(data.products.associateBy { it.product.id }),
                available = availableProducts,
                onRemove = onRemoveProducts,
                onAdd = onAddProducts,
                onDismissRequest = { showProducts = false }
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



        Column(modifier) {
            Costumer(
                state = data.costumer,
                onStateChange = { change -> onStateChange(pair.mapSecond { copy(costumer = change) }) }
            )
            RoomDivider()
            SelectableRoomTextField(
                label = "City",
                value = data.city?.city?.name ?: "No city",
                selected = showCities,
                onClick = { showCities = true },
            )
        }
    }
}
