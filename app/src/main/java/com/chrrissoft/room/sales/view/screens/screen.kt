package com.chrrissoft.room.sales.view.screens

import androidx.compose.runtime.Composable
import com.chrrissoft.room.categories.db.objects.CategoryWithRelationship
import com.chrrissoft.room.categories.view.states.CategoriesState
import com.chrrissoft.room.costumers.view.states.CostumersState
import com.chrrissoft.room.cross.db.objects.CategoriesAndSales
import com.chrrissoft.room.cross.db.objects.ProductsAndSales
import com.chrrissoft.room.cross.db.objects.SalesAndPromotions
import com.chrrissoft.room.cross.db.objects.SuppliersAndSales
import com.chrrissoft.room.cross.view.events.CrossRefEvent
import com.chrrissoft.room.cross.view.events.CrossRefEvent.OnDeleteCategoriesAndSales
import com.chrrissoft.room.cross.view.events.CrossRefEvent.OnDeleteProductsAndSales
import com.chrrissoft.room.cross.view.events.CrossRefEvent.OnDeleteSalesAndPromotions
import com.chrrissoft.room.cross.view.events.CrossRefEvent.OnDeleteSuppliersAndSales
import com.chrrissoft.room.cross.view.events.CrossRefEvent.OnSaveCategoriesAndSales
import com.chrrissoft.room.cross.view.events.CrossRefEvent.OnSaveProductsAndSales
import com.chrrissoft.room.cross.view.events.CrossRefEvent.OnSaveSalesAndPromotions
import com.chrrissoft.room.cross.view.events.CrossRefEvent.OnSaveSuppliersAndSales
import com.chrrissoft.room.cross.view.state.CrossRefState
import com.chrrissoft.room.orders.view.states.OrdersState
import com.chrrissoft.room.products.db.objects.ProductWithRelationship
import com.chrrissoft.room.products.view.states.ProductsState
import com.chrrissoft.room.promotions.db.objects.PromotionWithRelationship
import com.chrrissoft.room.promotions.view.states.PromotionsState
import com.chrrissoft.room.sales.db.objects.Sale
import com.chrrissoft.room.sales.db.objects.SaleWithNestedRelationship
import com.chrrissoft.room.sales.view.events.SalesEvent
import com.chrrissoft.room.sales.view.events.SalesEvent.OnChange
import com.chrrissoft.room.sales.view.events.SalesEvent.OnChangePage
import com.chrrissoft.room.sales.view.events.SalesEvent.OnCreate
import com.chrrissoft.room.sales.view.events.SalesEvent.OnDelete
import com.chrrissoft.room.sales.view.events.SalesEvent.OnOpen
import com.chrrissoft.room.sales.view.events.SalesEvent.OnSave
import com.chrrissoft.room.sales.view.states.SalesState
import com.chrrissoft.room.sales.view.ui.SaleList
import com.chrrissoft.room.sales.view.ui.SaleWithRelationship
import com.chrrissoft.room.sellers.view.states.SellersState
import com.chrrissoft.room.suppliers.db.objects.SupplierWithRelationship
import com.chrrissoft.room.suppliers.view.states.SuppliersState
import com.chrrissoft.room.ui.components.AlarmManagerSnackbar
import com.chrrissoft.room.ui.components.CommonScreen
import com.chrrissoft.room.utils.ResStateUtils.getSuccess
import com.chrrissoft.room.utils.Utils.uuid

@Composable
fun SalesScreen(
    state: SalesState,
    costumersState: CostumersState,
    ordersState: OrdersState,
    sellersState: SellersState,
    suppliersState: SuppliersState,
    categoriesState: CategoriesState,
    productsState: ProductsState,
    promotionsState: PromotionsState,
    crossState: CrossRefState,
    onEvent: (SalesEvent) -> Unit,
    onCrossEvent: (CrossRefEvent) -> Unit,
    onOpenDrawer: () -> Unit,
) {
    val id = state.detail.getSuccess()?.first
    val transformCategories = { data: Map<String, CategoryWithRelationship> ->
        data.mapNotNull { id?.let { id -> CategoriesAndSales(it.key, id) } }
    }

    val transformPromotions = { data: Map<String, PromotionWithRelationship> ->
        data.mapNotNull { id?.let { id -> SalesAndPromotions(id, it.key) } }
    }

    val transformSuppliers = { data: Map<String, SupplierWithRelationship> ->
        data.mapNotNull { id?.let { id -> SuppliersAndSales(it.value.supplier.id, id) } }
    }

    val transformProducts = { data: Map<String, ProductWithRelationship> ->
        data.mapNotNull { id?.let { id -> ProductsAndSales(uuid, it.key, id) } }
    }
    CommonScreen(
        page = state.page,
        title = "Sales",
        onChangePage = { onEvent(OnChangePage(it)) },
        onSave = { state.detail.getSuccess()?.let { onEvent(OnSave(it)) } },
        onCreate = { onEvent(OnCreate(it to SaleWithNestedRelationship(Sale(it)))) },
        onNavigation = onOpenDrawer,
        details = {
            SaleWithRelationship(
                state = state.detail,
                sellers = sellersState.listing,
                costumers = costumersState.listing,
                orders = ordersState.listing,
                suppliers = suppliersState.listing,
                onRemoveSuppliers = {
                    onCrossEvent(OnDeleteSuppliersAndSales(transformSuppliers(it)))
                },
                onAddSuppliers = {
                    onCrossEvent(OnSaveSuppliersAndSales(transformSuppliers(it)))
                },
                categories = categoriesState.listing,
                onRemoveCategories = {
                    onCrossEvent(OnDeleteCategoriesAndSales(transformCategories(it)))
                },
                onAddCategories = {
                    onCrossEvent(OnSaveCategoriesAndSales(transformCategories(it)))
                },
                products = productsState.listing,
                onRemoveProducts = { onCrossEvent(OnDeleteProductsAndSales) },
                onAddProducts = { onCrossEvent(OnSaveProductsAndSales(transformProducts(it))) },
                promotions = promotionsState.listing,
                onRemovePromotions = {
                    onCrossEvent(OnDeleteSalesAndPromotions(transformPromotions(it)))
                },
                onAddPromotions = {
                    onCrossEvent(OnSaveSalesAndPromotions(transformPromotions(it)))
                },
                onStateChange = { onEvent(OnChange(it)) },
            )
        },
        list = {
            SaleList(
                state = state.listing,
                onDelete = { onEvent(OnDelete(it)) },
                selected = setOf(state.detail.getSuccess()?.first),
                onSelect = { onEvent(OnOpen(it)) },
            )
        },
        snackbarHost = {
            AlarmManagerSnackbar(state = state.snackbar)
            AlarmManagerSnackbar(state = costumersState.snackbar)
            AlarmManagerSnackbar(state = ordersState.snackbar)
            AlarmManagerSnackbar(state = sellersState.snackbar)
            AlarmManagerSnackbar(state = suppliersState.snackbar)
            AlarmManagerSnackbar(state = categoriesState.snackbar)
            AlarmManagerSnackbar(state = productsState.snackbar)
            AlarmManagerSnackbar(state = promotionsState.snackbar)
            AlarmManagerSnackbar(state = crossState.snackbar)
        }
    )
}
