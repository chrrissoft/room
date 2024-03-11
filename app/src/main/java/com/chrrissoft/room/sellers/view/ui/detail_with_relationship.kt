package com.chrrissoft.room.sellers.view.ui

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
import com.chrrissoft.room.cities.db.objects.CityWithRelationship
import com.chrrissoft.room.cities.view.ui.CitiesListSheet
import com.chrrissoft.room.products.db.objects.ProductWithRelationship
import com.chrrissoft.room.products.view.ui.AndOrRemoveProductListSheet
import com.chrrissoft.room.sales.db.objects.SaleWithRelationship
import com.chrrissoft.room.sales.view.ui.AndOrRemoveSaleListSheet
import com.chrrissoft.room.sellers.db.objects.SellerWithNestedRelationship
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.app.ResState.Success
import com.chrrissoft.room.shared.view.ResState
import com.chrrissoft.room.shipments.db.objects.ShippingWithRelationship
import com.chrrissoft.room.shipments.view.ui.AndOrRemoveShippingListSheet
import com.chrrissoft.room.ui.components.RoomDivider
import com.chrrissoft.room.ui.components.SelectableRoomTextField
import com.chrrissoft.room.utils.PairUtils.mapSecond
import com.chrrissoft.room.utils.ResStateUtils.map
import com.chrrissoft.room.utils.Utils
import com.chrrissoft.room.utils.Utils.count

@Composable
fun SellerWithRelationship(
    state: ResState<Pair<String, SellerWithNestedRelationship>>,
    onStateChange: (Pair<String, SellerWithNestedRelationship>) -> Unit,
    cities: ResState<Map<String, CityWithRelationship>>,
    sales: ResState<Map<String, SaleWithRelationship>>,
    onRemoveSales: ((Map<String, SaleWithRelationship>) -> Unit)?,
    onAddSales: (Map<String, SaleWithRelationship>) -> Unit,
    products: ResState<Map<String, ProductWithRelationship>>,
    onRemoveProducts: ((Map<String, ProductWithRelationship>) -> Unit)?,
    onAddProducts: (Map<String, ProductWithRelationship>) -> Unit,
    shipments: ResState<Map<String, ShippingWithRelationship>>,
    onRemoveShipments: ((Map<String, ShippingWithRelationship>) -> Unit)?,
    onAddShipments: (Map<String, ShippingWithRelationship>) -> Unit,
    modifier: Modifier = Modifier,
) {
    ResState(state = state) { pair ->
        val data = remember(pair.second) { pair.second }

        var showCities by remember { mutableStateOf(value = false) }

        if (showCities) {
            CitiesListSheet(
                state = cities,
                selected = setOf(data.city.city.id),
                onSelect = {
                    val seller = data.seller.copy(cityId = it.first)
                    pair.mapSecond { copy(seller = seller, city = it.second) }
                        .let(onStateChange)
                },
                onDismissRequest = { showCities = false },
            )
        }


        var showSales by remember { mutableStateOf(value = false) }

        if (showSales) {
            var availableSales by remember(data.sales) { mutableStateOf(sales) }
            LaunchedEffect(data.sales) {
                sales.map { map -> map.filterNot { data.sales.contains(it.value) } }
                    .let { availableSales = it }
            }
            AndOrRemoveSaleListSheet(
                added = Success(data.sales.associateBy { it.sale.id }),
                available = availableSales,
                onRemove = onRemoveSales,
                onAdd = onAddSales,
                onDismissRequest = { showSales = false }
            )
        }


        var showProducts by remember { mutableStateOf(value = false) }

        if (showProducts) {
            AndOrRemoveProductListSheet(
                added = Success(data.products.associateBy { Utils.uuid }),
                available = products,
                onRemove = onRemoveProducts,
                onAdd = onAddProducts,
                onDismissRequest = { showProducts = false }
            )
        }


        var showShipments by remember { mutableStateOf(value = false) }

        if (showShipments) {
            var availableShipments by remember(shipments) { mutableStateOf(shipments) }
            LaunchedEffect(data.shipments) {
                availableShipments
                    .map { res -> res.filterNot { data.shipments.contains(it.value) } }
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


        Column(modifier.verticalScroll(rememberScrollState())) {
            Seller(
                state = data.seller,
                onStateChange = { onStateChange(pair.mapSecond { copy(seller = it) }) })
            RoomDivider()
            SelectableRoomTextField(
                label = "City",
                value = data.city.city.name,
                selected = showCities,
                onClick = { showCities = true }
            )
            SelectableRoomTextField(
                label = "Sales",
                value = data.sales.joinToString(limit = 3) { it.sale.name },
                selected = showSales,
                onClick = { showSales = true }
            )
            SelectableRoomTextField(
                label = "Products",
                selected = showProducts,
                onClick = { showProducts = true },
                value = data.products.groupBy { it.product.id }.toList().joinToString(limit = 3) {
                    it.second.first().product.name + it.second.count
                },
            )
            SelectableRoomTextField(
                label = "Shipments",
                selected = showShipments,
                value = data.shipments.joinToString(limit = 3) { it.shipping.name },
                onClick = { showShipments = true },
            )
        }
    }
}
