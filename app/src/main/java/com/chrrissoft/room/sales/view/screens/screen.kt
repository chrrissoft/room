package com.chrrissoft.room.sales.view.screens

import androidx.compose.runtime.Composable
import com.chrrissoft.room.costumers.view.states.CostumersState
import com.chrrissoft.room.orders.view.states.OrdersState
import com.chrrissoft.room.sales.db.objects.Sale
import com.chrrissoft.room.sales.db.objects.SaleWithRelationship
import com.chrrissoft.room.sales.view.events.SalesEvent
import com.chrrissoft.room.sales.view.states.SalesState
import com.chrrissoft.room.sales.view.ui.SaleWithRelationship
import com.chrrissoft.room.sales.view.ui.SaleList
import com.chrrissoft.room.sellers.view.states.SellersState
import com.chrrissoft.room.ui.components.AlarmManagerSnackbar
import com.chrrissoft.room.ui.components.CommonScreen
import com.chrrissoft.room.utils.ResStateUtils.getSuccess

@Composable
fun SalesScreen(
    state: SalesState,
    costumersState: CostumersState,
    ordersState: OrdersState,
    sellersState: SellersState,
    onEvent: (SalesEvent) -> Unit,
    onOpenDrawer: () -> Unit,
) {
    CommonScreen(
        page = state.page,
        title = "Sales",
        onChangePage = { onEvent(SalesEvent.OnChangePage(it)) },
        onSave = { state.detail.getSuccess()?.let { onEvent(SalesEvent.OnSave(it)) } },
        onCreate = { onEvent(SalesEvent.OnCreate(it to SaleWithRelationship(Sale(it)))) },
        onNavigation = onOpenDrawer,
        details = {
            SaleWithRelationship(
                state = state.detail,
                sellers = sellersState.listing,
                costumers = costumersState.listing,
                orders = ordersState.listing,
                onStateChange = { onEvent(SalesEvent.OnChange(it)) },
            )
        },
        list = {
            SaleList(
                state = state.listing,
                onDelete = { onEvent(SalesEvent.OnDelete(it)) },
                selected = setOf(state.detail.getSuccess()?.first),
                onSelect = { onEvent(SalesEvent.OnOpen(it)) },
            )
        },
        snackbarHost = { AlarmManagerSnackbar(state = state.snackbar) }
    )
}
