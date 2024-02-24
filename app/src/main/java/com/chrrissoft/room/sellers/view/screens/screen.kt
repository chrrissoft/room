package com.chrrissoft.room.sellers.view.screens

import androidx.compose.runtime.Composable
import com.chrrissoft.room.cities.view.states.CitiesState
import com.chrrissoft.room.sellers.db.objects.Seller
import com.chrrissoft.room.sellers.db.objects.SellerWithRelationship
import com.chrrissoft.room.sellers.view.events.SellersEvent
import com.chrrissoft.room.sellers.view.ui.SellerWithRelationship
import com.chrrissoft.room.sellers.view.ui.SellersList
import com.chrrissoft.room.sellers.view.states.SellersState
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.ui.components.AlarmManagerSnackbar
import com.chrrissoft.room.ui.components.CommonScreen
import com.chrrissoft.room.utils.ResStateUtils.getSuccess

@Composable
fun SellersScreen(
    state: SellersState,
    citiesState: CitiesState,
    onEvent: (SellersEvent) -> Unit,
    onOpenDrawer: () -> Unit,
) {
    CommonScreen(
        page = state.page,
        title = "Sellers",
        onChangePage = { onEvent(SellersEvent.OnChangePage(it)) },
        onSave = { state.seller.getSuccess()?.let { onEvent(SellersEvent.OnSave(it)) } },
        onCreate = { onEvent(SellersEvent.OnCreate(it to SellerWithRelationship(Seller(it)))) },
        onNavigation = onOpenDrawer,
        details = {
            SellerWithRelationship(
                state = state.seller,
                onStateChange = { onEvent(SellersEvent.OnChange(it)) },
                cities = citiesState.cities,
                orders = emptyList(),
                sales = ResState.None,
            )
        },
        list = {
            SellersList(
                state = state.sellers,
                onDelete = { onEvent(SellersEvent.OnDelete(it)) },
                selected = setOf(),
                onSelect = { onEvent(SellersEvent.OnOpen(it.first)) },
            )
        },
        snackbarHost = { AlarmManagerSnackbar(state = state.snackbar) }
    )
}
