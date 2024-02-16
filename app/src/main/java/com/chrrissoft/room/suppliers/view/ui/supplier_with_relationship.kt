package com.chrrissoft.room.suppliers.view.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.chrrissoft.room.categories.db.objects.CategoryWithRelationship
import com.chrrissoft.room.categories.view.ui.CategoryListSheet
import com.chrrissoft.room.cities.db.objects.CityWithRelationship
import com.chrrissoft.room.cities.view.ui.CitiesListSheet
import com.chrrissoft.room.products.db.objects.ProductWithRelationship
import com.chrrissoft.room.products.view.ui.ProductListSheet
import com.chrrissoft.room.sales.db.objects.SaleWithRelationship
import com.chrrissoft.room.sales.view.ui.SaleListSheet
import com.chrrissoft.room.suppliers.db.objects.SupplierWithRelationship
import com.chrrissoft.room.ui.components.SelectableRoomTextField

@Composable
fun SupplierWithRelationship(
    state: SupplierWithRelationship,
    onStateChange: (SupplierWithRelationship) -> Unit,
    sales: List<SaleWithRelationship>,
    cities: List<CityWithRelationship>,
    products: List<ProductWithRelationship>,
    categories: List<CategoryWithRelationship>,
    modifier: Modifier = Modifier,
) {
    var showSales by remember { mutableStateOf(value = false) }

    if (showSales) {
        val selected = remember(state.sales) { state.sales.mapTo(mutableSetOf()) { it.id } }
        SaleListSheet(
            state = sales,
            onSelect = { sale ->
                (if (state.sales.contains(sale.sale)) state.sales.minus(sale.sale)
                else state.sales.plus(sale.sale)).let { onStateChange(state.copy(sales = it)) }
            },
            selected = selected,
            onDismissRequest = { showSales = false })
    }


    var showCities by remember { mutableStateOf(value = false) }

    if (showCities) {
        val selected = remember(state.cities) { state.cities.mapTo(mutableSetOf()) { it.id } }
        CitiesListSheet(
            state = cities,
            onSelect = { city ->
                (if (state.cities.contains(city.city)) state.cities.minus(city.city)
                else state.cities.plus(city.city)).let { onStateChange(state.copy(cities = it)) }
            },
            selected = selected,
            onDismissRequest = { showCities = false })
    }


    var showProducts by remember { mutableStateOf(value = false) }

    if (showProducts) {
        val selected = remember(state.products) { state.products.mapTo(mutableSetOf()) { it.id } }
        ProductListSheet(
            state = products,
            onSelect = { product ->
                (if (state.products.contains(product.product)) state.products.minus(product.product)
                else state.products.plus(product.product)).let { onStateChange(state.copy(products = it)) }
            },
            selected = selected,
            onDismissRequest = { showProducts = false })
    }


    var showCategories by remember { mutableStateOf(value = false) }

    if (showCategories) {
        val selected =
            remember(state.categories) { state.categories.mapTo(mutableSetOf()) { it.id } }
        CategoryListSheet(
            state = categories,
            onSelect = { category ->
                (if (state.categories.contains(category.category)) state.categories.minus(category.category)
                else state.categories.plus(category.category)).let {
                    onStateChange(
                        state.copy(
                            categories = it
                        )
                    )
                }
            },
            selected = selected,
            onDismissRequest = { showCategories = false })
    }

    Column(modifier) {
        Supplier(state = state.supplier, onStateChange = { onStateChange(state.copy()) })
        SelectableRoomTextField(value = sales.joinToString { ", " }) { showSales = true }
        SelectableRoomTextField(value = cities.joinToString { ", " }) { showCities = true }
        SelectableRoomTextField(value = products.joinToString { ", " }) { showProducts = true }
        SelectableRoomTextField(value = categories.joinToString { ", " }) { showCategories = true }
    }
}
