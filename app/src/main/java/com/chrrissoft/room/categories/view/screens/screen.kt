package com.chrrissoft.room.categories.view.screens

import androidx.compose.runtime.Composable
import com.chrrissoft.room.categories.db.objects.Category
import com.chrrissoft.room.categories.db.objects.CategoryWithNestedRelationship
import com.chrrissoft.room.categories.view.events.CategoriesEvent
import com.chrrissoft.room.categories.view.events.CategoriesEvent.OnAddOrders
import com.chrrissoft.room.categories.view.events.CategoriesEvent.OnAddPromotions
import com.chrrissoft.room.categories.view.events.CategoriesEvent.OnAddSales
import com.chrrissoft.room.categories.view.events.CategoriesEvent.OnAddSuppliers
import com.chrrissoft.room.categories.view.events.CategoriesEvent.OnChange
import com.chrrissoft.room.categories.view.events.CategoriesEvent.OnChangePage
import com.chrrissoft.room.categories.view.events.CategoriesEvent.OnCreate
import com.chrrissoft.room.categories.view.events.CategoriesEvent.OnDelete
import com.chrrissoft.room.categories.view.events.CategoriesEvent.OnOpen
import com.chrrissoft.room.categories.view.events.CategoriesEvent.OnRemoveOrders
import com.chrrissoft.room.categories.view.events.CategoriesEvent.OnRemovePromotions
import com.chrrissoft.room.categories.view.events.CategoriesEvent.OnRemoveSales
import com.chrrissoft.room.categories.view.events.CategoriesEvent.OnRemoveSuppliers
import com.chrrissoft.room.categories.view.events.CategoriesEvent.OnSave
import com.chrrissoft.room.categories.view.states.CategoriesState
import com.chrrissoft.room.categories.view.ui.CategoriesList
import com.chrrissoft.room.categories.view.ui.CategoryWithRelationship
import com.chrrissoft.room.orders.view.states.OrdersState
import com.chrrissoft.room.promotions.view.states.PromotionsState
import com.chrrissoft.room.sales.view.states.SalesState
import com.chrrissoft.room.suppliers.view.states.SuppliersState
import com.chrrissoft.room.ui.components.AlarmManagerSnackbar
import com.chrrissoft.room.ui.components.CommonScreen
import com.chrrissoft.room.utils.ResStateUtils.getSuccess

@Composable
fun CategoriesScreen(
    state: CategoriesState,
    promotionsState: PromotionsState,
    ordersState: OrdersState,
    salesState: SalesState,
    suppliersState: SuppliersState,
    onEvent: (CategoriesEvent) -> Unit,
    onOpenDrawer: () -> Unit,
) {
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
                onRemovePromotions = { onEvent(OnAddPromotions()) },
                onAddPromotions = { onEvent(OnRemovePromotions()) },
                orders = ordersState.listing,
                onRemoveOrders = { onEvent(OnAddOrders()) },
                onAddOrders = { onEvent(OnRemoveOrders()) },
                sales = salesState.listing,
                onRemoveSales = { onEvent(OnAddSales()) },
                onAddSales = { onEvent(OnRemoveSales()) },
                suppliers = suppliersState.listing,
                onRemoveSuppliers = { onEvent(OnAddSuppliers()) },
                onAddSuppliers = { onEvent(OnRemoveSuppliers()) },
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
        snackbarHost = { AlarmManagerSnackbar(state = state.snackbar) }
    )
}
