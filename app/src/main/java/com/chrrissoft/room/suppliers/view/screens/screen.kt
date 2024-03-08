package com.chrrissoft.room.suppliers.view.screens

import androidx.compose.runtime.Composable
import com.chrrissoft.room.categories.view.states.CategoriesState
import com.chrrissoft.room.cities.view.states.CitiesState
import com.chrrissoft.room.products.view.states.ProductsState
import com.chrrissoft.room.sales.view.states.SalesState
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.suppliers.db.objects.Supplier
import com.chrrissoft.room.suppliers.db.objects.SupplierWithNestedRelationship
import com.chrrissoft.room.suppliers.view.events.SuppliersEvent
import com.chrrissoft.room.suppliers.view.events.SuppliersEvent.*
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
        onChangePage = { onEvent(OnChangePage(it)) },
        onSave = { state.detail.getSuccess()?.let { onEvent(OnSave(it)) } },
        onCreate = { onEvent(OnCreate(it to SupplierWithNestedRelationship(Supplier(it)))) },
        onNavigation = onOpenDrawer,
        details = {
            SupplierWithRelationship(
                state = state.detail,
                onStateChange = { onEvent(OnChange(it)) },
                cities = citiesState.listing,
                onRemoveCities = {},
                onAddCities = {},
                products = productsState.listing,
                onRemoveProducts = {},
                onAddProducts = {},
                sales = salesState.listing,
                onRemoveSales = {},
                onAddSales = {},
                categories = categoriesState.listing,
                onRemoveCategories = {},
                onAddCategories = {},
            )
        },
        list = {
            SuppliersList(
                state = state.listing,
                onDelete = { onEvent(OnDelete(it)) },
                selected = setOf(state.detail.getSuccess()?.first),
                onSelect = { onEvent(OnOpen(it)) },
            )
        },
        snackbarHost = { AlarmManagerSnackbar(state = state.snackbar) }
    )
}
