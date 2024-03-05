package com.chrrissoft.room.promotions.view.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.chrrissoft.room.categories.db.objects.CategoryWithRelationship
import com.chrrissoft.room.categories.view.ui.CategoryListSheet
import com.chrrissoft.room.orders.db.objects.OrderWithRelationship
import com.chrrissoft.room.orders.view.ui.OrderListSheet
import com.chrrissoft.room.products.db.objects.ProductWithRelationship
import com.chrrissoft.room.products.view.ui.ProductListSheet
import com.chrrissoft.room.promotions.db.objects.PromotionWithRelationship
import com.chrrissoft.room.sales.db.objects.SaleWithRelationship
import com.chrrissoft.room.sales.view.ui.SaleListSheet
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.view.ResState
import com.chrrissoft.room.ui.components.RoomDivider
import com.chrrissoft.room.ui.components.SelectableRoomTextField
import com.chrrissoft.room.utils.PairUtils.mapSecond

@Composable
fun PromotionWithRelationship(
    state: ResState<Pair<String, PromotionWithRelationship>>,
    onStateChange: (Pair<String, PromotionWithRelationship>) -> Unit,
    sales: ResState<Map<String, SaleWithRelationship>>,
    products: ResState<Map<String, ProductWithRelationship>>,
    categories: ResState<Map<String, CategoryWithRelationship>>,
    orders: ResState<Map<String, OrderWithRelationship>>,
    modifier: Modifier = Modifier,
) {
    ResState(state = state) { pair ->
        val data = remember(pair.second) { pair.second }

        var showSales by remember { mutableStateOf(value = false) }

        if (showSales) {
            val selected = remember(data.sales) { data.sales.mapTo(mutableSetOf()) { it.id } }
            SaleListSheet(
                state = sales,
                selected = selected,
                onSelect = { sale ->
                    (if (data.sales.contains(sale.second.sale))
                        data.sales.minus(sale.second.sale)
                    else data.sales.plus(sale.second.sale))
                        .let { onStateChange(pair.mapSecond { copy(sales = it) }) }
                },
                onDelete = {},
                onDismissRequest = { showSales = false })
        }


        var showProducts by remember { mutableStateOf(value = false) }

        if (showProducts) {
            val selected = remember(data.sales) { data.sales.mapTo(mutableSetOf()) { it.id } }
            ProductListSheet(
                state = products,
                selected = selected,
                onSelect = { sale ->
                    (if (data.products.contains(sale.second.product))
                        data.products.minus(sale.second.product)
                    else data.products.plus(sale.second.product))
                        .let { onStateChange(pair.mapSecond { copy(products = it) }) }
                },
                onDelete = {},
                onDismissRequest = { showProducts = false })
        }


        var showCategories by remember { mutableStateOf(value = false) }

        if (showCategories) {
            val selected = remember(data.sales) { data.sales.mapTo(mutableSetOf()) { it.id } }
            CategoryListSheet(
                state = categories,
                selected = selected,
                onSelect = { sale ->
                    (if (data.categories.contains(sale.second.category))
                        data.categories.minus(sale.second.category)
                    else data.categories.plus(sale.second.category))
                        .let { onStateChange(pair.mapSecond { copy(categories = it) }) }
                },
                onDelete = {},
                onDismissRequest = { showCategories = false })
        }


        var showOrders by remember { mutableStateOf(value = false) }

        if (showOrders) {
            val selected = remember(data.sales) { data.sales.mapTo(mutableSetOf()) { it.id } }
            OrderListSheet(
                state = orders,
                selected = selected,
                onSelect = { sale ->
                    (if (data.orders.contains(sale.second.order))
                        data.orders.minus(sale.second.order)
                    else data.orders.plus(sale.second.order))
                        .let { onStateChange(pair.mapSecond { copy(orders = it) }) }
                },
                onDismissRequest = { showOrders = false })
        }


        Column(modifier) {
            Promotion(
                state = data.promotion,
                onStateChange = { onStateChange(pair.mapSecond { copy(promotion = it) }) })
            RoomDivider()
            SelectableRoomTextField(
                label = "Sales",
                selected = showSales,
                onClick = { showSales = true },
                value = data.sales.map { it.id }.joinToString { ", " },
            )
            SelectableRoomTextField(
                label = "Products",
                selected = showProducts,
                onClick = { showProducts = true },
                value = data.products.map { it.name }.joinToString { ", " },
            )
            SelectableRoomTextField(
                label = "Categories",
                selected = showCategories,
                onClick = { showCategories = true },
                value = data.categories.map { it.name }.joinToString { ", " },
            )
            SelectableRoomTextField(
                label = "Orders",
                selected = showOrders,
                onClick = { showOrders = true },
                value = data.orders.map { it.id }.joinToString { ", " },
            )
        }
    }
}
