package com.chrrissoft.room.suppliers.view.screens

import androidx.compose.runtime.Composable
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.suppliers.db.objects.Supplier
import com.chrrissoft.room.suppliers.db.objects.SupplierWithRelationship
import com.chrrissoft.room.suppliers.view.ui.SupplierWithRelationship
import com.chrrissoft.room.suppliers.view.events.SuppliersEvent
import com.chrrissoft.room.suppliers.view.events.SuppliersEvent.OnSave
import com.chrrissoft.room.suppliers.view.states.SuppliersState
import com.chrrissoft.room.suppliers.view.ui.SuppliersList
import com.chrrissoft.room.ui.components.AlarmManagerSnackbar
import com.chrrissoft.room.ui.components.CommonScreen
import com.chrrissoft.room.utils.ResStateUtils.getSuccess

@Composable
fun SuppliersScreen(
    state: SuppliersState,
    onEvent: (SuppliersEvent) -> Unit,
    onOpenDrawer: () -> Unit,
) {
    CommonScreen(
        page = state.page,
        title = "Suppliers",
        onChangePage = { onEvent(SuppliersEvent.OnChangePage(it)) },
        onSave = { state.supplier.getSuccess()?.let { onEvent(OnSave(it)) } },
        onCreate = { onEvent(SuppliersEvent.OnCreate(it to SupplierWithRelationship(Supplier(it)))) },
        onNavigation = onOpenDrawer,
        details = {
            SupplierWithRelationship(
                state = state.supplier,
                onStateChange = { onEvent(SuppliersEvent.OnChange(it)) },
                sales = ResState.None,
                cities = ResState.None,
                products = emptyList(),
                categories = ResState.None,
            )
        },
        list = {
            SuppliersList(
                state = state.suppliers,
                onDelete = { onEvent(SuppliersEvent.OnDelete(it)) },
                selected = setOf(),
                onSelect = { onEvent(SuppliersEvent.OnOpen(it.first)) },
            )
        },
        snackbarHost = { AlarmManagerSnackbar(state = state.snackbar) }
    )
}
