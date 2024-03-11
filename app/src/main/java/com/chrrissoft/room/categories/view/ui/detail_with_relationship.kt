package com.chrrissoft.room.categories.view.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.chrrissoft.room.categories.db.objects.CategoryWithNestedRelationship
import com.chrrissoft.room.promotions.db.objects.PromotionWithRelationship
import com.chrrissoft.room.promotions.view.ui.AndOrRemovePromotionListSheet
import com.chrrissoft.room.sales.db.objects.SaleWithRelationship
import com.chrrissoft.room.sales.view.ui.AndOrRemoveSaleListSheet
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.app.ResState.Success
import com.chrrissoft.room.shared.view.ResState
import com.chrrissoft.room.suppliers.db.objects.SupplierWithRelationship
import com.chrrissoft.room.suppliers.view.ui.AndOrRemoveSupplierListSheet
import com.chrrissoft.room.ui.components.RoomDivider
import com.chrrissoft.room.ui.components.SelectableRoomTextField
import com.chrrissoft.room.utils.ResStateUtils.map

@Composable
fun CategoryWithRelationship(
    state: ResState<Pair<String, CategoryWithNestedRelationship>>,
    onStateChange: (Pair<String, CategoryWithNestedRelationship>) -> Unit,
    promotions: ResState<Map<String, PromotionWithRelationship>>,
    onRemovePromotions: (Map<String, PromotionWithRelationship>) -> Unit,
    onAddPromotions: (Map<String, PromotionWithRelationship>) -> Unit,
    sales: ResState<Map<String, SaleWithRelationship>>,
    onRemoveSales: (Map<String, SaleWithRelationship>) -> Unit,
    onAddSales: (Map<String, SaleWithRelationship>) -> Unit,
    suppliers: ResState<Map<String, SupplierWithRelationship>>,
    onRemoveSuppliers: (Map<String, SupplierWithRelationship>) -> Unit,
    onAddSuppliers: (Map<String, SupplierWithRelationship>) -> Unit,
    modifier: Modifier = Modifier,
) {
    ResState(state = state) { res ->
        val data = remember(res) { res.second }

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


        Column(modifier) {
            Category(
                state = data.category,
                onStateChange = { onStateChange(res.first to data.copy(category = it)) })
            RoomDivider()
            SelectableRoomTextField(
                label = "Promotions",
                selected = showPromotions,
                onClick = { showPromotions = true },
                value = data.promotions.joinToString(limit = 3) { it.promotion.name },
            )
            SelectableRoomTextField(
                label = "Sales",
                selected = showSales,
                onClick = { showSales = true },
                value = data.sales.joinToString(limit = 3) { it.sale.payment.name },
            )
            SelectableRoomTextField(
                label = "Suppliers",
                selected = showSuppliers,
                onClick = { showSuppliers = true },
                value = data.suppliers.joinToString(limit = 3) { it.supplier.name },
            )
        }
    }
}
