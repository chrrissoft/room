package com.chrrissoft.room.categories.view.screens

import androidx.compose.runtime.Composable
import com.chrrissoft.room.categories.db.objects.Category
import com.chrrissoft.room.categories.db.objects.CategoryWithRelationship
import com.chrrissoft.room.categories.view.events.CategoriesEvent
import com.chrrissoft.room.categories.view.states.CategoriesState
import com.chrrissoft.room.categories.view.ui.CategoriesList
import com.chrrissoft.room.categories.view.ui.CategoryWithRelationship
import com.chrrissoft.room.orders.view.states.OrdersState
import com.chrrissoft.room.promotions.view.states.PromotionsState
import com.chrrissoft.room.sales.view.states.SalesState
import com.chrrissoft.room.ui.components.AlarmManagerSnackbar
import com.chrrissoft.room.ui.components.CommonScreen
import com.chrrissoft.room.utils.ResStateUtils.getSuccess

@Composable
fun CategoriesScreen(
    state: CategoriesState,
    ordersState: OrdersState,
    promotionsState: PromotionsState,
    salesState: SalesState,
    onEvent: (CategoriesEvent) -> Unit,
    onOpenDrawer: () -> Unit,
) {
    CommonScreen(
        page = state.page,
        title = "Categories",
        onChangePage = { onEvent(CategoriesEvent.OnChangePage(it)) },
        onSave = { state.detail.getSuccess()?.let { onEvent(CategoriesEvent.OnSave(it)) } },
        onCreate = { onEvent(CategoriesEvent.OnCreate(it to CategoryWithRelationship(Category(it)))) },
        onNavigation = onOpenDrawer,
        details = {
            CategoryWithRelationship(
                state = state.detail,
                onStateChange = { onEvent(CategoriesEvent.OnChange(it)) },
                orders = ordersState.listing,
                promotions = promotionsState.listing,
                sales = salesState.listing,
            )
        },
        list = {
            CategoriesList(
                state = state.listing,
                onDelete = { onEvent(CategoriesEvent.OnDelete(it)) },
                selected = setOf(state.detail.getSuccess()?.first),
                onSelect = { onEvent(CategoriesEvent.OnOpen(it)) },
            )
        },
        snackbarHost = { AlarmManagerSnackbar(state = state.snackbar) }
    )
}
