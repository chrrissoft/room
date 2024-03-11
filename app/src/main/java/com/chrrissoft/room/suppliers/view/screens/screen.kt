package com.chrrissoft.room.suppliers.view.screens

import androidx.compose.runtime.Composable
import com.chrrissoft.room.categories.db.objects.CategoryWithRelationship
import com.chrrissoft.room.categories.view.states.CategoriesState
import com.chrrissoft.room.cities.db.objects.CityWithRelationship
import com.chrrissoft.room.cities.view.states.CitiesState
import com.chrrissoft.room.cross.db.objects.CitiesAndSuppliers
import com.chrrissoft.room.cross.db.objects.ProductsAndSuppliers
import com.chrrissoft.room.cross.db.objects.SuppliersAndCategories
import com.chrrissoft.room.cross.db.objects.SuppliersAndSales
import com.chrrissoft.room.cross.view.events.CrossRefEvent
import com.chrrissoft.room.cross.view.events.CrossRefEvent.OnDeleteCitiesAndSuppliers
import com.chrrissoft.room.cross.view.events.CrossRefEvent.OnDeleteProductsAndSuppliers
import com.chrrissoft.room.cross.view.events.CrossRefEvent.OnDeleteSuppliersAndCategories
import com.chrrissoft.room.cross.view.events.CrossRefEvent.OnDeleteSuppliersAndSales
import com.chrrissoft.room.cross.view.events.CrossRefEvent.OnSaveCitiesAndSuppliers
import com.chrrissoft.room.cross.view.events.CrossRefEvent.OnSaveProductsAndSuppliers
import com.chrrissoft.room.cross.view.events.CrossRefEvent.OnSaveSuppliersAndCategories
import com.chrrissoft.room.cross.view.events.CrossRefEvent.OnSaveSuppliersAndSales
import com.chrrissoft.room.cross.view.state.CrossRefState
import com.chrrissoft.room.products.db.objects.ProductWithRelationship
import com.chrrissoft.room.products.view.states.ProductsState
import com.chrrissoft.room.sales.db.objects.SaleWithRelationship
import com.chrrissoft.room.sales.view.states.SalesState
import com.chrrissoft.room.suppliers.db.objects.Supplier
import com.chrrissoft.room.suppliers.db.objects.SupplierWithNestedRelationship
import com.chrrissoft.room.suppliers.view.events.SuppliersEvent
import com.chrrissoft.room.suppliers.view.events.SuppliersEvent.OnChange
import com.chrrissoft.room.suppliers.view.events.SuppliersEvent.OnChangePage
import com.chrrissoft.room.suppliers.view.events.SuppliersEvent.OnCreate
import com.chrrissoft.room.suppliers.view.events.SuppliersEvent.OnDelete
import com.chrrissoft.room.suppliers.view.events.SuppliersEvent.OnOpen
import com.chrrissoft.room.suppliers.view.events.SuppliersEvent.OnSave
import com.chrrissoft.room.suppliers.view.states.SuppliersState
import com.chrrissoft.room.suppliers.view.ui.SupplierWithRelationship
import com.chrrissoft.room.suppliers.view.ui.SuppliersList
import com.chrrissoft.room.ui.components.AlarmManagerSnackbar
import com.chrrissoft.room.ui.components.CommonScreen
import com.chrrissoft.room.utils.ResStateUtils.getSuccess
import com.chrrissoft.room.utils.Utils.uuid

@Composable
fun SuppliersScreen(
    state: SuppliersState,
    salesState: SalesState,
    citiesState: CitiesState,
    productsState: ProductsState,
    categoriesState: CategoriesState,
    crossState: CrossRefState,
    onEvent: (SuppliersEvent) -> Unit,
    onCrossEvent: (CrossRefEvent) -> Unit,
    onOpenDrawer: () -> Unit,
) {
    val id = state.detail.getSuccess()?.first
    val transformProducts = { data: Map<String, ProductWithRelationship> ->
        data.mapNotNull { id?.let { id -> ProductsAndSuppliers(it.key, id) } }
    }

    val transformSales = { data: Map<String, SaleWithRelationship> ->
        data.mapNotNull { id?.let { id -> SuppliersAndSales(id, it.key) } }
    }

    val transformCategories = { data: Map<String, CategoryWithRelationship> ->
        data.mapNotNull { id?.let { id -> SuppliersAndCategories(id, it.key) } }
    }

    val transformCities = { data: Map<String, CityWithRelationship> ->
        data.mapNotNull { id?.let { id -> CitiesAndSuppliers(uuid, it.key, id) } }
    }

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
                onRemoveCities = { onCrossEvent(OnDeleteCitiesAndSuppliers) },
                onAddCities = { onCrossEvent(OnSaveCitiesAndSuppliers(transformCities(it))) },
                products = productsState.listing,
                onRemoveProducts = {
                    onCrossEvent(OnDeleteProductsAndSuppliers(transformProducts(it)))
                },
                onAddProducts = {
                    onCrossEvent(OnSaveProductsAndSuppliers(transformProducts(it)))
                },
                sales = salesState.listing,
                onRemoveSales = { onCrossEvent(OnDeleteSuppliersAndSales(transformSales(it))) },
                onAddSales = { onCrossEvent(OnSaveSuppliersAndSales(transformSales(it))) },
                categories = categoriesState.listing,
                onRemoveCategories = {
                    onCrossEvent(OnDeleteSuppliersAndCategories(transformCategories(it)))
                },
                onAddCategories = {
                    onCrossEvent(OnSaveSuppliersAndCategories(transformCategories(it)))
                },
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
        snackbarHost = {
            AlarmManagerSnackbar(state = state.snackbar)
            AlarmManagerSnackbar(state = salesState.snackbar)
            AlarmManagerSnackbar(state = citiesState.snackbar)
            AlarmManagerSnackbar(state = productsState.snackbar)
            AlarmManagerSnackbar(state = categoriesState.snackbar)
            AlarmManagerSnackbar(state = crossState.snackbar)
        },
    )
}
