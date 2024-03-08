package com.chrrissoft.room.sellers.view.screens

import androidx.compose.runtime.Composable
import com.chrrissoft.room.cities.view.states.CitiesState
import com.chrrissoft.room.sales.view.states.SalesState
import com.chrrissoft.room.sellers.db.objects.Seller
import com.chrrissoft.room.sellers.db.objects.SellerWithNestedRelationship
import com.chrrissoft.room.sellers.view.events.SellersEvent
import com.chrrissoft.room.sellers.view.states.SellersState
import com.chrrissoft.room.sellers.view.ui.SellerWithRelationship
import com.chrrissoft.room.sellers.view.ui.SellersList
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.ui.components.AlarmManagerSnackbar
import com.chrrissoft.room.ui.components.CommonScreen
import com.chrrissoft.room.utils.ResStateUtils.getSuccess

@Composable
fun SellersScreen(
    state: SellersState,
    citiesState: CitiesState,
    salesState: SalesState,
    onEvent: (SellersEvent) -> Unit,
    onOpenDrawer: () -> Unit,
) {
    CommonScreen(
        page = state.page,
        title = "Sellers",
        onChangePage = { onEvent(SellersEvent.OnChangePage(it)) },
        onSave = { state.detail.getSuccess()?.let { onEvent(SellersEvent.OnSave(it)) } },
        onCreate = { onEvent(SellersEvent.OnCreate(it to SellerWithNestedRelationship(Seller(it)))) },
        onNavigation = onOpenDrawer,
        details = {
            SellerWithRelationship(
                state = state.detail,
                onStateChange = { onEvent(SellersEvent.OnChange(it)) },
                cities = citiesState.listing,
                sales = salesState.listing,
                onRemoveSales = {},
                onAddSales = {},
            )
        },
        list = {
            SellersList(
                state = state.listing,
                onDelete = { onEvent(SellersEvent.OnDelete(it)) },
                selected = setOf(state.detail.getSuccess()?.first),
                onSelect = { onEvent(SellersEvent.OnOpen(it)) },
            )
        },
        snackbarHost = { AlarmManagerSnackbar(state = state.snackbar) }
    )
}
