package com.chrrissoft.room.products.view.screens

import androidx.compose.runtime.Composable
import com.chrrissoft.room.costumers.db.objects.CostumerWithRelationship
import com.chrrissoft.room.costumers.view.states.CostumersState
import com.chrrissoft.room.cross.db.objects.CostumersAndProducts
import com.chrrissoft.room.cross.db.objects.ProductsAndSales
import com.chrrissoft.room.cross.db.objects.ProductsAndSuppliers
import com.chrrissoft.room.cross.db.objects.SellersAndProducts
import com.chrrissoft.room.cross.view.events.CrossRefEvent
import com.chrrissoft.room.cross.view.events.CrossRefEvent.OnDeleteCostumersAndProducts
import com.chrrissoft.room.cross.view.events.CrossRefEvent.OnDeleteProductsAndSales
import com.chrrissoft.room.cross.view.events.CrossRefEvent.OnDeleteProductsAndSuppliers
import com.chrrissoft.room.cross.view.events.CrossRefEvent.OnDeleteSellersAndProducts
import com.chrrissoft.room.cross.view.events.CrossRefEvent.OnSaveCostumersAndProducts
import com.chrrissoft.room.cross.view.events.CrossRefEvent.OnSaveProductsAndSales
import com.chrrissoft.room.cross.view.events.CrossRefEvent.OnSaveProductsAndSuppliers
import com.chrrissoft.room.cross.view.events.CrossRefEvent.OnSaveSellersAndProducts
import com.chrrissoft.room.cross.view.state.CrossRefState
import com.chrrissoft.room.products.db.objects.Product
import com.chrrissoft.room.products.db.objects.ProductWithNestedRelationship
import com.chrrissoft.room.products.view.events.ProductsEvent
import com.chrrissoft.room.products.view.events.ProductsEvent.OnChange
import com.chrrissoft.room.products.view.events.ProductsEvent.OnChangePage
import com.chrrissoft.room.products.view.events.ProductsEvent.OnCreate
import com.chrrissoft.room.products.view.events.ProductsEvent.OnDelete
import com.chrrissoft.room.products.view.events.ProductsEvent.OnOpen
import com.chrrissoft.room.products.view.events.ProductsEvent.OnSave
import com.chrrissoft.room.products.view.states.ProductsState
import com.chrrissoft.room.products.view.ui.ProductList
import com.chrrissoft.room.products.view.ui.ProductWithRelationship
import com.chrrissoft.room.promotions.view.states.PromotionsState
import com.chrrissoft.room.sales.db.objects.SaleWithRelationship
import com.chrrissoft.room.sales.view.states.SalesState
import com.chrrissoft.room.sellers.db.objects.SellerWithRelationship
import com.chrrissoft.room.sellers.view.states.SellersState
import com.chrrissoft.room.suppliers.db.objects.SupplierWithRelationship
import com.chrrissoft.room.suppliers.view.states.SuppliersState
import com.chrrissoft.room.ui.components.AlarmManagerSnackbar
import com.chrrissoft.room.ui.components.CommonScreen
import com.chrrissoft.room.utils.ResStateUtils.getSuccess
import com.chrrissoft.room.utils.Utils.uuid

@Composable
fun ProductsScreen(
    state: ProductsState,
    promotionsState: PromotionsState,
    costumersState: CostumersState,
    salesState: SalesState,
    suppliersState: SuppliersState,
    sellersState: SellersState,
    crossState: CrossRefState,
    onEvent: (ProductsEvent) -> Unit,
    onCrossEvent: (CrossRefEvent) -> Unit,
    onOpenDrawer: () -> Unit,
) {
    val id = state.detail.getSuccess()?.first
    val transformSuppliers = { data: Map<String, SupplierWithRelationship> ->
        data.mapNotNull { id?.let { id -> ProductsAndSuppliers(id, it.key) } }
    }

    val transformCostumers = { data: Map<String, CostumerWithRelationship> ->
        data.mapNotNull { id?.let { id -> CostumersAndProducts(uuid, it.key, id) } }
    }

    val transformSales = { data: Map<String, SaleWithRelationship> ->
        data.mapNotNull { id?.let { id -> ProductsAndSales(uuid, id, it.key) } }
    }

    val transformSellers = { data: Map<String, SellerWithRelationship> ->
        data.mapNotNull { id?.let { id -> SellersAndProducts(uuid, it.key, id) } }
    }
    CommonScreen(
        page = state.page,
        title = "Products",
        onChangePage = { onEvent(OnChangePage(it)) },
        onSave = { state.detail.getSuccess()?.let { onEvent(OnSave(it)) } },
        onCreate = { onEvent(OnCreate(it to ProductWithNestedRelationship(Product(it)))) },
        onNavigation = onOpenDrawer,
        details = {
            ProductWithRelationship(
                state = state.detail,
                promotions = promotionsState.listing,
                costumers = costumersState.listing,
                onRemoveCostumers = { onCrossEvent(OnDeleteCostumersAndProducts) },
                onAddCostumers = {
                    onCrossEvent(OnSaveCostumersAndProducts(transformCostumers(it)))
                },
                sales = salesState.listing,
                onRemoveSales = { onCrossEvent(OnDeleteProductsAndSales) },
                onAddSales = { onCrossEvent(OnSaveProductsAndSales(transformSales(it))) },
                suppliers = suppliersState.listing,
                onRemoveSuppliers = {
                    onCrossEvent(OnDeleteProductsAndSuppliers(transformSuppliers(it)))
                },
                onAddSuppliers = {
                    onCrossEvent(OnSaveProductsAndSuppliers(transformSuppliers(it)))
                },
                sellers = sellersState.listing,
                onRemoveSellers = { onCrossEvent(OnDeleteSellersAndProducts) },
                onAddSellers = { onCrossEvent(OnSaveSellersAndProducts(transformSellers(it))) },
                onStateChange = { onEvent(OnChange(it)) },
            )
        },
        list = {
            ProductList(
                state = state.listing,
                onDelete = { onEvent(OnDelete(it)) },
                selected = setOf(state.detail.getSuccess()?.first),
                onSelect = { onEvent(OnOpen(it)) },
            )
        },
        snackbarHost = {
            AlarmManagerSnackbar(state = state.snackbar)
            AlarmManagerSnackbar(state = promotionsState.snackbar)
            AlarmManagerSnackbar(state = costumersState.snackbar)
            AlarmManagerSnackbar(state = salesState.snackbar)
            AlarmManagerSnackbar(state = suppliersState.snackbar)
            AlarmManagerSnackbar(state = sellersState.snackbar)
            AlarmManagerSnackbar(state = crossState.snackbar)
        },
    )
}
