package com.chrrissoft.room.suppliers.view.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.chrrissoft.room.categories.db.objects.CategoryWithRelationship
import com.chrrissoft.room.categories.view.ui.AndOrRemoveCategoryListSheet
import com.chrrissoft.room.cities.db.objects.CityWithRelationship
import com.chrrissoft.room.cities.view.ui.AndOrRemoveCityListSheet
import com.chrrissoft.room.products.db.objects.ProductWithRelationship
import com.chrrissoft.room.products.view.ui.AndOrRemoveProductListSheet
import com.chrrissoft.room.sales.db.objects.SaleWithRelationship
import com.chrrissoft.room.sales.view.ui.AndOrRemoveSaleListSheet
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.app.ResState.Success
import com.chrrissoft.room.shared.view.ResState
import com.chrrissoft.room.suppliers.db.objects.SupplierWithNestedRelationship
import com.chrrissoft.room.ui.components.RoomDivider
import com.chrrissoft.room.ui.components.SelectableRoomTextField
import com.chrrissoft.room.utils.PairUtils.mapSecond
import com.chrrissoft.room.utils.ResStateUtils.map

@Composable
fun SupplierWithRelationship(
    state: ResState<Pair<String, SupplierWithNestedRelationship>>,
    onStateChange: (Pair<String, SupplierWithNestedRelationship>) -> Unit,
    cities: ResState<Map<String, CityWithRelationship>>,
    onRemoveCities: (Map<String, CityWithRelationship>) -> Unit,
    onAddCities: (Map<String, CityWithRelationship>) -> Unit,
    products: ResState<Map<String, ProductWithRelationship>>,
    onRemoveProducts: (Map<String, ProductWithRelationship>) -> Unit,
    onAddProducts: (Map<String, ProductWithRelationship>) -> Unit,
    sales: ResState<Map<String, SaleWithRelationship>>,
    onRemoveSales: (Map<String, SaleWithRelationship>) -> Unit,
    onAddSales: (Map<String, SaleWithRelationship>) -> Unit,
    categories: ResState<Map<String, CategoryWithRelationship>>,
    onRemoveCategories: (Map<String, CategoryWithRelationship>) -> Unit,
    onAddCategories: (Map<String, CategoryWithRelationship>) -> Unit,
    modifier: Modifier = Modifier,
) {
    ResState(state = state) { pair ->
        val data by remember(pair.second) { mutableStateOf(pair.second) }

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


        var showSales by remember { mutableStateOf(value = false) }

        if (showSales) {
            var availableSales by remember(sales) { mutableStateOf(sales) }
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


        var showCategories by remember { mutableStateOf(value = false) }

        if (showCategories) {
            var availableCategories by remember(categories) { mutableStateOf(categories) }
            LaunchedEffect(data.categories) {
                categories.map { map -> map.filterNot { data.categories.contains(it.value) } }
                    .let { availableCategories = it }
            }
            AndOrRemoveCategoryListSheet(
                added = Success(data.categories.associateBy { it.category.id }),
                available = availableCategories,
                onRemove = onRemoveCategories,
                onAdd = onAddCategories,
                onDismissRequest = { showCategories = false }
            )
        }

        Column(modifier) {
            Supplier(
                state = data.supplier,
                onStateChange = { onStateChange(pair.mapSecond { copy(supplier = it) }) })
            RoomDivider()
            SelectableRoomTextField(
                label = "Cities",
                selected = showCities,
                onClick = { showCities = true },
                value = data.cities.joinToString(limit = 3) { it.city.name },
            )
            SelectableRoomTextField(
                label = "Products",
                selected = showProducts,
                onClick = { showProducts = true },
                value = data.products.joinToString(limit = 3) { it.product.name },
            )
            SelectableRoomTextField(
                label = "Sales",
                selected = showSales,
                onClick = { showSales = true },
                value = data.sales.joinToString(limit = 3) { it.sale.id },
            )
            SelectableRoomTextField(
                label = "Categories",
                selected = showCategories,
                onClick = { showCategories = true },
                value = data.categories.joinToString(limit = 3) { it.category.name },
            )
        }
    }
}
