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
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.view.ResState
import com.chrrissoft.room.suppliers.db.objects.SupplierWithRelationship
import com.chrrissoft.room.ui.components.RoomDivider
import com.chrrissoft.room.ui.components.SelectableRoomTextField
import com.chrrissoft.room.utils.PairUtils.mapSecond

@Composable
fun SupplierWithRelationship(
    state: ResState<Pair<String, SupplierWithRelationship>>,
    onStateChange: (Pair<String, SupplierWithRelationship>) -> Unit,
    sales: ResState<Map<String, SaleWithRelationship>>,
    cities: ResState<Map<String, CityWithRelationship>>,
    products: List<ProductWithRelationship>,
    categories: ResState<Map<String, CategoryWithRelationship>>,
    modifier: Modifier = Modifier,
) {
    ResState(state = state) { data ->

        var showSales by remember { mutableStateOf(value = false) }

        if (showSales) {
            val selected =
                remember(data.second.sales) { data.second.sales.mapTo(mutableSetOf()) { it.id } }
            SaleListSheet(
                state = sales,
                onSelect = { sale ->
                    (if (data.second.sales.contains(sale.second.sale)) data.second.sales.minus(
                        sale.second.sale
                    )
                    else data.second.sales.plus(sale.second.sale)).let {
                        onStateChange(data.mapSecond {
                            copy(
                                sales = it
                            )
                        })
                    }
                },
                selected = selected,
                onDelete = {},
                onDismissRequest = { showSales = false })
        }


        var showCities by remember { mutableStateOf(value = false) }

        if (showCities) {
            val selected =
                remember(data.second.cities) { data.second.cities.mapTo(mutableSetOf()) { it.id } }
            CitiesListSheet(
                state = cities,
                onDelete = { },
                onSelect = { city ->
                    (if (data.second.cities.contains(city.second.city)) data.second.cities.minus(
                        city.second.city
                    )
                    else data.second.cities.plus(city.second.city)).let {
                        onStateChange(data.mapSecond {
                            copy(
                                cities = it
                            )
                        })
                    }
                },
                selected = selected,
                onDismissRequest = { showCities = false })
        }


        var showProducts by remember { mutableStateOf(value = false) }

        if (showProducts) {
            val selected =
                remember(data.second.products) { data.second.products.mapTo(mutableSetOf()) { it.id } }
            ProductListSheet(
                state = products,
                onSelect = { product ->
                    (if (data.second.products.contains(product.product)) data.second.products.minus(
                        product.product
                    )
                    else data.second.products.plus(product.product)).let {
                        onStateChange(data.mapSecond { copy(products = it) })
                    }
                },
                selected = selected,
                onDismissRequest = { showProducts = false })
        }


        var showCategories by remember { mutableStateOf(value = false) }

        if (showCategories) {
            val selected =
                remember(data.second.categories) { data.second.categories.mapTo(mutableSetOf()) { it.id } }
            CategoryListSheet(
                state = categories,
                onSelect = { category ->
                    (if (data.second.categories.contains(category.second.category)) data.second.categories.minus(
                        category.second.category
                    )
                    else data.second.categories.plus(category.second.category)).let {
                        onStateChange(
                            data.mapSecond { copy(categories = it) })
                    }
                },
                onDelete = {},
                selected = selected,
                onDismissRequest = { showCategories = false })
        }

        Column(modifier) {
            Supplier(state = data.second.supplier, onStateChange = { onStateChange(data.mapSecond { copy(supplier = it) }) })
            RoomDivider()
//        SelectableRoomTextField(value = sales.joinToString { ", " }) { showSales = true }
//        SelectableRoomTextField(value = cities.joinToString { ", " }) { showCities = true }
            SelectableRoomTextField(value = products.joinToString { ", " }) { showProducts = true }
//        SelectableRoomTextField(value = categories.joinToString { ", " }) { showCategories = true }
        }
    }
}
