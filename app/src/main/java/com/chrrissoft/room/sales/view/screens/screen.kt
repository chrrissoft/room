package com.chrrissoft.room.sales.view.screens

import androidx.compose.runtime.Composable
import com.chrrissoft.room.costumers.view.states.CostumersState
import com.chrrissoft.room.orders.view.states.OrdersState
import com.chrrissoft.room.sales.db.objects.Sale
import com.chrrissoft.room.sales.db.objects.SaleWithRelationship
import com.chrrissoft.room.sales.view.events.SalesEvent
import com.chrrissoft.room.sales.view.states.SalesState
import com.chrrissoft.room.sales.view.ui.SaleWithRelationship
import com.chrrissoft.room.sales.view.ui.SalesList
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
        onSave = { state.sale.getSuccess()?.let { onEvent(SalesEvent.OnSave(it)) } },
        onCreate = { onEvent(SalesEvent.OnCreate(it to SaleWithRelationship(Sale(it)))) },
        onNavigation = onOpenDrawer,
        details = {
            SaleWithRelationship(
                state = state.sale,
                sellers = sellersState.sellers,
                costumers = costumersState.costumers,
                orders = ordersState.orders,
                onStateChange = { onEvent(SalesEvent.OnChange(it)) },
            )
        },
        list = {
            SalesList(
                state = state.sales,
                onDelete = { onEvent(SalesEvent.OnDelete(it)) },
                selected = setOf(),
                onSelect = { onEvent(SalesEvent.OnOpen(it.first)) },
            )
        },
        snackbarHost = { AlarmManagerSnackbar(state = state.snackbar) }
    )
}
