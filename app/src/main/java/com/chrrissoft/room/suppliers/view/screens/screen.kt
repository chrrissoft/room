package com.chrrissoft.room.suppliers.view.screens

import androidx.compose.runtime.Composable
import com.chrrissoft.room.categories.view.states.CategoriesState
import com.chrrissoft.room.cities.view.states.CitiesState
import com.chrrissoft.room.products.view.states.ProductsState
import com.chrrissoft.room.sales.view.states.SalesState
import com.chrrissoft.room.suppliers.db.objects.Supplier
import com.chrrissoft.room.suppliers.db.objects.SupplierWithRelationship
import com.chrrissoft.room.suppliers.view.events.SuppliersEvent
import com.chrrissoft.room.suppliers.view.events.SuppliersEvent.OnSave
import com.chrrissoft.room.suppliers.view.states.SuppliersState
import com.chrrissoft.room.suppliers.view.ui.SupplierWithRelationship
import com.chrrissoft.room.suppliers.view.ui.SuppliersList
import com.chrrissoft.room.ui.components.AlarmManagerSnackbar
import com.chrrissoft.room.ui.components.CommonScreen
import com.chrrissoft.room.utils.ResStateUtils.getSuccess

@Composable
fun SuppliersScreen(
    state: SuppliersState,
    salesState: SalesState,
    citiesState: CitiesState,
    productsState: ProductsState,
    categoriesState: CategoriesState,
    onEvent: (SuppliersEvent) -> Unit,
    onOpenDrawer: () -> Unit,
) {
    CommonScreen(
        page = state.page,
        title = "Suppliers",
        onChangePage = { onEvent(SuppliersEvent.OnChangePage(it)) },
        onSave = { state.detail.getSuccess()?.let { onEvent(OnSave(it)) } },
        onCreate = { onEvent(SuppliersEvent.OnCreate(it to SupplierWithRelationship(Supplier(it)))) },
        onNavigation = onOpenDrawer,
        details = {
            SupplierWithRelationship(
                state = state.detail,
                onStateChange = { onEvent(SuppliersEvent.OnChange(it)) },
                sales = salesState.listing,
                cities = citiesState.listing,
                products = productsState.listing,
                categories = categoriesState.listing,
            )
        },
        list = {
            SuppliersList(
                state = state.listing,
                onDelete = { onEvent(SuppliersEvent.OnDelete(it)) },
                selected = setOf(state.detail.getSuccess()?.first),
                onSelect = { onEvent(SuppliersEvent.OnOpen(it)) },
            )
        },
        snackbarHost = { AlarmManagerSnackbar(state = state.snackbar) }
    )
}
