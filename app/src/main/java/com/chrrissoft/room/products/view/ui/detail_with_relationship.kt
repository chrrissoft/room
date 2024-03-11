package com.chrrissoft.room.products.view.ui

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
import com.chrrissoft.room.costumers.db.objects.CostumerWithRelationship
import com.chrrissoft.room.costumers.view.ui.AndOrRemoveCostumerListSheet
import com.chrrissoft.room.products.db.objects.ProductWithNestedRelationship
import com.chrrissoft.room.promotions.db.objects.PromotionWithRelationship
import com.chrrissoft.room.promotions.view.ui.PromotionListSheet
import com.chrrissoft.room.sales.db.objects.SaleWithRelationship
import com.chrrissoft.room.sales.view.ui.AndOrRemoveSaleListSheet
import com.chrrissoft.room.sellers.db.objects.SellerWithRelationship
import com.chrrissoft.room.sellers.view.ui.AndOrRemoveSellerListSheet
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.app.ResState.Success
import com.chrrissoft.room.shared.view.ResState
import com.chrrissoft.room.suppliers.db.objects.SupplierWithRelationship
import com.chrrissoft.room.suppliers.view.ui.AndOrRemoveSupplierListSheet
import com.chrrissoft.room.ui.components.RoomDivider
import com.chrrissoft.room.ui.components.SelectableRoomTextField
import com.chrrissoft.room.utils.PairUtils.mapSecond
import com.chrrissoft.room.utils.ResStateUtils.map
import com.chrrissoft.room.utils.Utils
import com.chrrissoft.room.utils.Utils.count

@Composable
fun ProductWithRelationship(
    state: ResState<Pair<String, ProductWithNestedRelationship>>,
    onStateChange: (Pair<String, ProductWithNestedRelationship>) -> Unit,
    promotions: ResState<Map<String, PromotionWithRelationship>>,
    costumers: ResState<Map<String, CostumerWithRelationship>>,
    onRemoveCostumers: (Map<String, CostumerWithRelationship>) -> Unit,
    onAddCostumers: (Map<String, CostumerWithRelationship>) -> Unit,
    sales: ResState<Map<String, SaleWithRelationship>>,
    onRemoveSales: (Map<String, SaleWithRelationship>) -> Unit,
    onAddSales: (Map<String, SaleWithRelationship>) -> Unit,
    suppliers: ResState<Map<String, SupplierWithRelationship>>,
    onRemoveSuppliers: (Map<String, SupplierWithRelationship>) -> Unit,
    onAddSuppliers: (Map<String, SupplierWithRelationship>) -> Unit,
    sellers: ResState<Map<String, SellerWithRelationship>>,
    onRemoveSellers: (Map<String, SellerWithRelationship>) -> Unit,
    onAddSellers: (Map<String, SellerWithRelationship>) -> Unit,
    modifier: Modifier = Modifier,
) {
    ResState(state = state) { pair ->
        val data by remember(pair.second) { mutableStateOf(pair.second) }

        var showPromotions by remember { mutableStateOf(value = false) }

        if (showPromotions) {
            PromotionListSheet(
                state = promotions,
                selected = setOf(data.promotion?.promotion?.id),
                onSelect = {
                    val order = data.product.copy(promotionId = it.first)
                    pair.mapSecond { copy(product = order, promotion = it.second) }
                        .let(onStateChange)
                },
                onDismissRequest = { showPromotions = false })
        }


        var showCostumers by remember { mutableStateOf(value = false) }

        if (showCostumers) {
            AndOrRemoveCostumerListSheet(
                added = Success(data.costumers.associateBy { Utils.uuid }),
                available = costumers,
                onRemove = onRemoveCostumers,
                onAdd = onAddCostumers,
                onDismissRequest = { showCostumers = false }
            )
        }


        var showSales by remember { mutableStateOf(value = false) }

        if (showSales) {
            AndOrRemoveSaleListSheet(
                added = Success(data.sales.associateBy { Utils.uuid }),
                available = sales,
                onRemove = onRemoveSales,
                onAdd = onAddSales,
                onDismissRequest = { showSales = false }
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

        var showSellers by remember { mutableStateOf(value = false) }

        if (showSellers) {
            AndOrRemoveSellerListSheet(
                added = Success(data.sellers.associateBy { Utils.uuid }),
                available = sellers,
                onRemove = onRemoveSellers,
                onAdd = onAddSellers,
                onDismissRequest = { showSellers = false }
            )
        }


        Column(modifier.verticalScroll(rememberScrollState())) {
            Product(
                state = data.product,
                onStateChange = { onStateChange(pair.mapSecond { copy(product = it)}) }
            )
            RoomDivider()
            SelectableRoomTextField(
                label = "Promotion",
                selected = showPromotions,
                value = data.promotion?.promotion?.name ?: "No promotions",
                onClick = { showPromotions = true },
            )
            SelectableRoomTextField(
                label = "Costumers",
                selected = showCostumers,
                value = data.costumers.groupBy { it.costumer.id }.toList().joinToString(limit = 3) {
                    it.second.first().costumer.name.first + it.second.count
                },
                onClick = { showCostumers = true },
            )
            SelectableRoomTextField(
                label = "Sales",
                selected = showSales,
                value = data.sales.groupBy { it.sale.id }.toList().joinToString(limit = 3) {
                    it.second.first().sale.name + it.second.count
                },
                onClick = { showSales = true },
            )
            SelectableRoomTextField(
                label = "Suppliers",
                selected = showSuppliers,
                value = data.suppliers.joinToString(limit = 3) { it.supplier.name },
                onClick = { showSuppliers = true },
            )
            SelectableRoomTextField(
                label = "Sellers",
                selected = showSellers,
                value = data.sellers.groupBy { it.seller.id }.toList().joinToString(limit = 3) {
                    it.second.first().seller.name.first + it.second.count
                },
                onClick = { showSellers = true },
            )
        }
    }
}
