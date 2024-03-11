package com.chrrissoft.room.sales.view.ui

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
import com.chrrissoft.room.categories.db.objects.CategoryWithRelationship
import com.chrrissoft.room.categories.view.ui.AndOrRemoveCategoryListSheet
import com.chrrissoft.room.costumers.db.objects.CostumerWithRelationship
import com.chrrissoft.room.costumers.view.ui.CostumerListSheet
import com.chrrissoft.room.orders.db.objects.OrderWithRelationship
import com.chrrissoft.room.orders.view.ui.OrderListSheet
import com.chrrissoft.room.products.db.objects.ProductWithRelationship
import com.chrrissoft.room.products.view.ui.AndOrRemoveProductListSheet
import com.chrrissoft.room.promotions.db.objects.PromotionWithRelationship
import com.chrrissoft.room.promotions.view.ui.AndOrRemovePromotionListSheet
import com.chrrissoft.room.sales.db.objects.SaleWithNestedRelationship
import com.chrrissoft.room.sellers.db.objects.SellerWithRelationship
import com.chrrissoft.room.sellers.view.ui.SellerListSheet
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.app.ResState.Success
import com.chrrissoft.room.shared.view.ResState
import com.chrrissoft.room.suppliers.db.objects.SupplierWithRelationship
import com.chrrissoft.room.suppliers.view.ui.AndOrRemoveSupplierListSheet
import com.chrrissoft.room.ui.components.RoomDivider
import com.chrrissoft.room.ui.components.SelectableRoomTextField
import com.chrrissoft.room.utils.PairUtils.mapSecond
import com.chrrissoft.room.utils.ResStateUtils.map
import com.chrrissoft.room.utils.Utils.count
import com.chrrissoft.room.utils.Utils.uuid

@Composable
fun SaleWithRelationship(
    state: ResState<Pair<String, SaleWithNestedRelationship>>,
    onStateChange: (Pair<String, SaleWithNestedRelationship>) -> Unit,
    orders: ResState<Map<String, OrderWithRelationship>>,
    sellers: ResState<Map<String, SellerWithRelationship>>,
    costumers: ResState<Map<String, CostumerWithRelationship>>,
    suppliers: ResState<Map<String, SupplierWithRelationship>>,
    onRemoveSuppliers: (Map<String, SupplierWithRelationship>) -> Unit,
    onAddSuppliers: (Map<String, SupplierWithRelationship>) -> Unit,
    categories: ResState<Map<String, CategoryWithRelationship>>,
    onRemoveCategories: (Map<String, CategoryWithRelationship>) -> Unit,
    onAddCategories: (Map<String, CategoryWithRelationship>) -> Unit,
    products: ResState<Map<String, ProductWithRelationship>>,
    onRemoveProducts: (Map<String, ProductWithRelationship>) -> Unit,
    onAddProducts: (Map<String, ProductWithRelationship>) -> Unit,
    promotions: ResState<Map<String, PromotionWithRelationship>>,
    onRemovePromotions: (Map<String, PromotionWithRelationship>) -> Unit,
    onAddPromotions: (Map<String, PromotionWithRelationship>) -> Unit,
    modifier: Modifier = Modifier,
) {
    ResState(state = state) { pair ->
        val data = remember(pair.second) { pair.second }

        var showSellers by remember { mutableStateOf(value = false) }

        if (showSellers) {
            SellerListSheet(
                state = sellers,
                selected = setOf(data.seller.seller.id),
                onSelect = {
                    val sale = data.sale.copy(sellerId = it.first)
                    pair.mapSecond { copy(sale = sale, seller = it.second) }
                        .let(onStateChange)
                },
                onDismissRequest = { showSellers = false })
        }


        var showCostumers by remember { mutableStateOf(value = false) }

        if (showCostumers) {
            CostumerListSheet(
                state = costumers,
                selected = setOf(data.costumer.costumer.id),
                onSelect = {
                    val sale = data.sale.copy(costumerId = it.first)
                    pair.mapSecond { copy(sale = sale, costumer = it.second) }
                        .let(onStateChange)
                },
                onDismissRequest = { showCostumers = false })
        }


        var showOrders by remember { mutableStateOf(value = false) }

        if (showOrders) {
            OrderListSheet(
                state = orders,
                onSelect = {
                    val sale = data.sale.copy(orderId = it.first)
                    pair.mapSecond { copy(sale = sale, order = it.second) }
                        .let(onStateChange)
                },
                selected = setOf(data.order?.order?.id),
                onDismissRequest = { showOrders = false })
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


        var showProducts by remember { mutableStateOf(value = false) }

        if (showProducts) {
            AndOrRemoveProductListSheet(
                added = Success(data.products.associateBy { uuid }),
                available = products,
                onRemove = onRemoveProducts,
                onAdd = onAddProducts,
                onDismissRequest = { showProducts = false }
            )
        }


        var showPromotions by remember { mutableStateOf(value = false) }

        if (showPromotions) {
            var availablePromotions by remember(promotions) { mutableStateOf(promotions) }
            LaunchedEffect(data.promotions) {
                promotions.map { map -> map.filterNot { data.promotions.contains(it.value) } }
                    .let { availablePromotions = it }
            }

            AndOrRemovePromotionListSheet(
                added = Success(data.promotions.associateBy { it.promotion.id }),
                available = availablePromotions,
                onRemove = onRemovePromotions,
                onAdd = onAddPromotions,
                onDismissRequest = { showPromotions = false }
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
                added = Success(data.suppliers.associateBy { it.supplier.id }),
                available = availableSuppliers,
                onRemove = onRemoveSuppliers,
                onAdd = onAddSuppliers,
                onDismissRequest = { showSuppliers = false }
            )
        }


        Column(modifier.verticalScroll(rememberScrollState())) {
            Sale(
                state = data.sale,
                onStateChange = { onStateChange(pair.mapSecond { copy(sale = it) }) })
            RoomDivider()
            SelectableRoomTextField(
                label = "Seller",
                selected = showSellers,
                value = data.seller.seller.name.first,
                onClick = { showSellers = true },
            )
            SelectableRoomTextField(
                label = "Costumer",
                selected = showCostumers,
                value = data.costumer.costumer.name.first,
                onClick = { showCostumers = true },
            )
            SelectableRoomTextField(
                label = "Order",
                selected = showOrders,
                value = data.order?.order?.direction?.street ?: "No Order",
                onClick = { showOrders = true },
            )
            SelectableRoomTextField(
                label = "Categories",
                selected = showCategories,
                value = data.categories.joinToString(limit = 3) { it.category.name },
                onClick = { showCategories = true },
            )
            SelectableRoomTextField(
                label = "Products",
                selected = showProducts,
                value = data.products.groupBy { it.product.id }.toList().joinToString(limit = 3) {
                    it.second.first().product.name + it.second.count
                },
                onClick = { showProducts = true },
            )
            SelectableRoomTextField(
                label = "Promotions",
                selected = showPromotions,
                value = data.promotions.joinToString(limit = 3) { it.promotion.name },
                onClick = { showPromotions = true },
            )
            SelectableRoomTextField(
                label = "Suppliers",
                selected = showSuppliers,
                value = data.suppliers.joinToString(limit = 3) { it.supplier.name },
                onClick = { showSuppliers = true },
            )
        }
    }
}
