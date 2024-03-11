package com.chrrissoft.room.categories.view.screens

import androidx.compose.runtime.Composable
import com.chrrissoft.room.categories.db.objects.Category
import com.chrrissoft.room.categories.db.objects.CategoryWithNestedRelationship
import com.chrrissoft.room.categories.view.events.CategoriesEvent
import com.chrrissoft.room.categories.view.events.CategoriesEvent.OnChange
import com.chrrissoft.room.categories.view.events.CategoriesEvent.OnChangePage
import com.chrrissoft.room.categories.view.events.CategoriesEvent.OnCreate
import com.chrrissoft.room.categories.view.events.CategoriesEvent.OnDelete
import com.chrrissoft.room.categories.view.events.CategoriesEvent.OnOpen
import com.chrrissoft.room.categories.view.events.CategoriesEvent.OnSave
import com.chrrissoft.room.categories.view.states.CategoriesState
import com.chrrissoft.room.categories.view.ui.CategoriesList
import com.chrrissoft.room.categories.view.ui.CategoryWithRelationship
import com.chrrissoft.room.cross.db.objects.CategoriesAndPromotions
import com.chrrissoft.room.cross.db.objects.CategoriesAndSales
import com.chrrissoft.room.cross.db.objects.SuppliersAndCategories
import com.chrrissoft.room.cross.view.events.CrossRefEvent
import com.chrrissoft.room.cross.view.events.CrossRefEvent.OnDeleteCategoriesAndPromotions
import com.chrrissoft.room.cross.view.events.CrossRefEvent.OnDeleteCategoriesAndSales
import com.chrrissoft.room.cross.view.events.CrossRefEvent.OnDeleteSuppliersAndCategories
import com.chrrissoft.room.cross.view.events.CrossRefEvent.OnSaveCategoriesAndPromotions
import com.chrrissoft.room.cross.view.events.CrossRefEvent.OnSaveCategoriesAndSales
import com.chrrissoft.room.cross.view.events.CrossRefEvent.OnSaveSuppliersAndCategories
import com.chrrissoft.room.cross.view.state.CrossRefState
import com.chrrissoft.room.promotions.db.objects.PromotionWithRelationship
import com.chrrissoft.room.promotions.view.states.PromotionsState
import com.chrrissoft.room.sales.db.objects.SaleWithRelationship
import com.chrrissoft.room.sales.view.states.SalesState
import com.chrrissoft.room.suppliers.db.objects.SupplierWithRelationship
import com.chrrissoft.room.suppliers.view.states.SuppliersState
import com.chrrissoft.room.ui.components.AlarmManagerSnackbar
import com.chrrissoft.room.ui.components.CommonScreen
import com.chrrissoft.room.utils.ResStateUtils.getSuccess

@Composable
fun CategoriesScreen(
    state: CategoriesState,
    promotionsState: PromotionsState,
    salesState: SalesState,
    suppliersState: SuppliersState,
    crossState: CrossRefState,
    onEvent: (CategoriesEvent) -> Unit,
    onCrossEvent: (CrossRefEvent) -> Unit,
    onOpenDrawer: () -> Unit,
) {
    val id = state.detail.getSuccess()?.first
    val transformPromotions = { data: Map<String, PromotionWithRelationship> ->
        data.mapNotNull { id?.let { id -> CategoriesAndPromotions(id, it.key) } }
    }

    val transformSales = { data: Map<String, SaleWithRelationship> ->
        data.mapNotNull { id?.let { id -> CategoriesAndSales(id, it.key) } }
    }

    val transformSuppliers = { data: Map<String, SupplierWithRelationship> ->
        data.mapNotNull { id?.let { id -> SuppliersAndCategories(it.value.supplier.id, id) } }
    }

    CommonScreen(
        page = state.page,
        title = "Categories",
        onChangePage = { onEvent(OnChangePage(it)) },
        onSave = { state.detail.getSuccess()?.let { onEvent(OnSave(it)) } },
        onCreate = { onEvent(OnCreate(it to CategoryWithNestedRelationship(Category(it)))) },
        onNavigation = onOpenDrawer,
        details = {
            CategoryWithRelationship(
                state = state.detail,
                onStateChange = { onEvent(OnChange(it)) },
                promotions = promotionsState.listing,
                onRemovePromotions = {
                    onCrossEvent(OnDeleteCategoriesAndPromotions(transformPromotions(it)))
                },
                onAddPromotions = {
                    onCrossEvent(OnSaveCategoriesAndPromotions(transformPromotions(it)))
                },
                sales = salesState.listing,
                onRemoveSales = { onCrossEvent(OnDeleteCategoriesAndSales(transformSales(it))) },
                onAddSales = { onCrossEvent(OnSaveCategoriesAndSales(transformSales(it))) },
                suppliers = suppliersState.listing,
                onRemoveSuppliers = {
                    onCrossEvent(OnDeleteSuppliersAndCategories(transformSuppliers(it)))
                },
                onAddSuppliers = {
                    onCrossEvent(OnSaveSuppliersAndCategories(transformSuppliers(it)))
                },
            )
        },
        list = {
            CategoriesList(
                state = state.listing,
                onDelete = { onEvent(OnDelete(it)) },
                selected = setOf(state.detail.getSuccess()?.first),
                onSelect = { onEvent(OnOpen(it)) },
            )
        },
        snackbarHost = {
            AlarmManagerSnackbar(state = state.snackbar)
            AlarmManagerSnackbar(state = promotionsState.snackbar)
            AlarmManagerSnackbar(state = salesState.snackbar)
            AlarmManagerSnackbar(state = suppliersState.snackbar)
            AlarmManagerSnackbar(state = crossState.snackbar)
        }
    )
}
