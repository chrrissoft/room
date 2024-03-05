package com.chrrissoft.room.products.view.screens

import androidx.compose.runtime.Composable
import com.chrrissoft.room.products.db.objects.Product
import com.chrrissoft.room.products.db.objects.ProductWithRelationship
import com.chrrissoft.room.products.view.events.ProductsEvent
import com.chrrissoft.room.products.view.states.ProductsState
import com.chrrissoft.room.products.view.ui.ProductList
import com.chrrissoft.room.products.view.ui.ProductWithRelationship
import com.chrrissoft.room.promotions.view.states.PromotionsState
import com.chrrissoft.room.ui.components.AlarmManagerSnackbar
import com.chrrissoft.room.ui.components.CommonScreen
import com.chrrissoft.room.utils.ResStateUtils.getSuccess

@Composable
fun ProductsScreen(
    state: ProductsState,
    promotionsState: PromotionsState,
    onEvent: (ProductsEvent) -> Unit,
    onOpenDrawer: () -> Unit,
) {
    CommonScreen(
        page = state.page,
        title = "Products",
        onChangePage = { onEvent(ProductsEvent.OnChangePage(it)) },
        onSave = { state.detail.getSuccess()?.let { onEvent(ProductsEvent.OnSave(it)) } },
        onCreate = { onEvent(ProductsEvent.OnCreate(it to ProductWithRelationship(Product(it)))) },
        onNavigation = onOpenDrawer,
        details = {
            ProductWithRelationship(
                state = state.detail,
                onStateChange = { onEvent(ProductsEvent.OnChange(it)) },
                promotions = promotionsState.listing,
            )
        },
        list = {
            ProductList(
                state = state.listing,
                onDelete = { onEvent(ProductsEvent.OnDelete(it)) },
                selected = setOf(state.detail.getSuccess()?.first),
                onSelect = { onEvent(ProductsEvent.OnOpen(it)) },
            )
        },
        snackbarHost = { AlarmManagerSnackbar(state = state.snackbar) }
    )
}
