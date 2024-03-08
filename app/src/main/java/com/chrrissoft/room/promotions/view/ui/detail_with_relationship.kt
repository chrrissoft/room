package com.chrrissoft.room.promotions.view.ui

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
import com.chrrissoft.room.orders.db.objects.OrderWithRelationship
import com.chrrissoft.room.orders.view.ui.AndOrRemoveOrderListSheet
import com.chrrissoft.room.products.db.objects.ProductWithRelationship
import com.chrrissoft.room.products.view.ui.AndOrRemoveProductListSheet
import com.chrrissoft.room.promotions.db.objects.PromotionNestedWithRelationship
import com.chrrissoft.room.sales.db.objects.SaleWithRelationship
import com.chrrissoft.room.sales.view.ui.AndOrRemoveSaleListSheet
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.app.ResState.Success
import com.chrrissoft.room.shared.view.ResState
import com.chrrissoft.room.ui.components.RoomDivider
import com.chrrissoft.room.ui.components.SelectableRoomTextField
import com.chrrissoft.room.utils.PairUtils.mapSecond
import com.chrrissoft.room.utils.ResStateUtils.map

@Composable
fun PromotionWithRelationship(
    state: ResState<Pair<String, PromotionNestedWithRelationship>>,
    onStateChange: (Pair<String, PromotionNestedWithRelationship>) -> Unit,
    sales: ResState<Map<String, SaleWithRelationship>>,
    onRemoveSales: (Map<String, SaleWithRelationship>) -> Unit,
    onAddSales: (Map<String, SaleWithRelationship>) -> Unit,
    products: ResState<Map<String, ProductWithRelationship>>,
    onRemoveProducts: (Map<String, ProductWithRelationship>) -> Unit,
    onAddProducts: (Map<String, ProductWithRelationship>) -> Unit,
    orders: ResState<Map<String, OrderWithRelationship>>,
    onRemoveOrders: (Map<String, OrderWithRelationship>) -> Unit,
    onAddOrders: (Map<String, OrderWithRelationship>) -> Unit,
    categories: ResState<Map<String, CategoryWithRelationship>>,
    onRemoveCategories: (Map<String, CategoryWithRelationship>) -> Unit,
    onAddCategories: (Map<String, CategoryWithRelationship>) -> Unit,
    modifier: Modifier = Modifier,
) {
    ResState(state = state) { pair ->
        val data = remember(pair.second) { pair.second }

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
            Promotion(
                state = data.promotion,
                onStateChange = { onStateChange(pair.mapSecond { copy(promotion = it) }) })
            RoomDivider()
            SelectableRoomTextField(
                label = "Sales",
                selected = showSales,
                onClick = { showSales = true },
                value = data.sales.joinToString(limit = 3) { it.sale.id },
            )
            SelectableRoomTextField(
                label = "Products",
                selected = showProducts,
                onClick = { showProducts = true },
                value = data.products.joinToString(limit = 3) { it.product.name },
            )
            SelectableRoomTextField(
                label = "Categories",
                selected = showCategories,
                onClick = { showCategories = true },
                value = data.categories.joinToString(limit = 3) { it.category.name },
            )
            SelectableRoomTextField(
                label = "Orders",
                selected = showOrders,
                onClick = { showOrders = true },
                value = data.orders.joinToString(limit = 3) { it.order.id },
            )
        }
    }
}
