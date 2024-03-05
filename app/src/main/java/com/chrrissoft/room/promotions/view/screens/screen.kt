package com.chrrissoft.room.promotions.view.screens

import androidx.compose.runtime.Composable
import com.chrrissoft.room.categories.view.states.CategoriesState
import com.chrrissoft.room.orders.view.states.OrdersState
import com.chrrissoft.room.products.view.states.ProductsState
import com.chrrissoft.room.promotions.db.objects.Promotion
import com.chrrissoft.room.promotions.db.objects.PromotionWithRelationship
import com.chrrissoft.room.promotions.view.events.PromotionsEvent
import com.chrrissoft.room.promotions.view.states.PromotionsState
import com.chrrissoft.room.promotions.view.ui.PromotionList
import com.chrrissoft.room.promotions.view.ui.PromotionWithRelationship
import com.chrrissoft.room.sales.view.states.SalesState
import com.chrrissoft.room.ui.components.AlarmManagerSnackbar
import com.chrrissoft.room.ui.components.CommonScreen
import com.chrrissoft.room.utils.ResStateUtils.getSuccess

@Composable
fun PromotionsScreen(
    state: PromotionsState,
    salesState: SalesState,
    productsState: ProductsState,
    categoriesState: CategoriesState,
    ordersState: OrdersState,
    onEvent: (PromotionsEvent) -> Unit,
    onOpenDrawer: () -> Unit,
) {
    CommonScreen(
        page = state.page,
        title = "Promotions",
        onChangePage = { onEvent(PromotionsEvent.OnChangePage(it)) },
        onSave = { state.detail.getSuccess()?.let { onEvent(PromotionsEvent.OnSave(it)) } },
        onCreate = { onEvent(PromotionsEvent.OnCreate(it to PromotionWithRelationship(Promotion(it)))) },
        onNavigation = onOpenDrawer,
        details = {
            PromotionWithRelationship(
                state = state.detail,
                onStateChange = { onEvent(PromotionsEvent.OnChange(it)) },
                sales = salesState.listing,
                products = productsState.listing,
                categories = categoriesState.listing,
                orders = ordersState.listing,
            )
        },
        list = {
            PromotionList(
                state = state.listing,
                onDelete = { onEvent(PromotionsEvent.OnDelete(it)) },
                selected = setOf(state.detail.getSuccess()?.first),
                onSelect = { onEvent(PromotionsEvent.OnOpen(it)) },
            )
        },
        snackbarHost = { AlarmManagerSnackbar(state = state.snackbar) }
    )
}
