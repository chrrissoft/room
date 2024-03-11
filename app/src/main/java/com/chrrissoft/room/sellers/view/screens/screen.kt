package com.chrrissoft.room.sellers.view.screens

import androidx.compose.runtime.Composable
import com.chrrissoft.room.cities.view.states.CitiesState
import com.chrrissoft.room.cross.db.objects.SellersAndProducts
import com.chrrissoft.room.cross.db.objects.SellersAndShipments
import com.chrrissoft.room.cross.view.events.CrossRefEvent
import com.chrrissoft.room.cross.view.events.CrossRefEvent.OnDeleteSellersAndProducts
import com.chrrissoft.room.cross.view.events.CrossRefEvent.OnDeleteSellersAndShipments
import com.chrrissoft.room.cross.view.events.CrossRefEvent.OnSaveSellersAndProducts
import com.chrrissoft.room.cross.view.events.CrossRefEvent.OnSaveSellersAndShipments
import com.chrrissoft.room.cross.view.state.CrossRefState
import com.chrrissoft.room.products.db.objects.ProductWithRelationship
import com.chrrissoft.room.products.view.states.ProductsState
import com.chrrissoft.room.sales.view.events.SalesEvent
import com.chrrissoft.room.sales.view.states.SalesState
import com.chrrissoft.room.sellers.db.objects.Seller
import com.chrrissoft.room.sellers.db.objects.SellerWithNestedRelationship
import com.chrrissoft.room.sellers.view.events.SellersEvent
import com.chrrissoft.room.sellers.view.events.SellersEvent.OnChange
import com.chrrissoft.room.sellers.view.events.SellersEvent.OnChangePage
import com.chrrissoft.room.sellers.view.events.SellersEvent.OnCreate
import com.chrrissoft.room.sellers.view.events.SellersEvent.OnDelete
import com.chrrissoft.room.sellers.view.events.SellersEvent.OnOpen
import com.chrrissoft.room.sellers.view.events.SellersEvent.OnSave
import com.chrrissoft.room.sellers.view.states.SellersState
import com.chrrissoft.room.sellers.view.ui.SellerWithRelationship
import com.chrrissoft.room.sellers.view.ui.SellersList
import com.chrrissoft.room.shipments.db.objects.ShippingWithRelationship
import com.chrrissoft.room.shipments.view.states.ShipmentsState
import com.chrrissoft.room.ui.components.AlarmManagerSnackbar
import com.chrrissoft.room.ui.components.CommonScreen
import com.chrrissoft.room.utils.ResStateUtils.getSuccess
import com.chrrissoft.room.utils.Utils.uuid

@Composable
fun SellersScreen(
    state: SellersState,
    citiesState: CitiesState,
    salesState: SalesState,
    productsState: ProductsState,
    shipmentsState: ShipmentsState,
    crossState: CrossRefState,
    onEvent: (SellersEvent) -> Unit,
    onSalesEvent: (SalesEvent) -> Unit,
    onCrossEvent: (CrossRefEvent) -> Unit,
    onOpenDrawer: () -> Unit,
) {
    val id = state.detail.getSuccess()?.first
    val transShipments = { data: Map<String, ShippingWithRelationship> ->
        data.mapNotNull { id?.let { id -> SellersAndShipments(id, it.key) } }
    }
    val transformProducts = { data: Map<String, ProductWithRelationship> ->
        data.mapNotNull { id?.let { id -> SellersAndProducts(uuid, id, it.key) } }
    }
    CommonScreen(
        page = state.page,
        title = "Sellers",
        onChangePage = { onEvent(OnChangePage(it)) },
        onSave = { state.detail.getSuccess()?.let { onEvent(OnSave(it)) } },
        onCreate = { onEvent(OnCreate(it to SellerWithNestedRelationship(Seller(it)))) },
        onNavigation = onOpenDrawer,
        details = {
            SellerWithRelationship(
                state = state.detail,
                onStateChange = { onEvent(OnChange(it)) },
                cities = citiesState.listing,
                sales = salesState.listing,
                onRemoveSales = null,
                onAddSales = { data ->
                    id ?: return@SellerWithRelationship
                    data.mapValues { it.value.sale.copy(sellerId = id) }
                        .also { onSalesEvent(SalesEvent.OnSaveRaw(it)) }
                },
                products = productsState.listing,
                onRemoveProducts = { onCrossEvent(OnDeleteSellersAndProducts) },
                onAddProducts = { onCrossEvent(OnSaveSellersAndProducts(transformProducts(it))) },
                shipments = shipmentsState.listing,
                onRemoveShipments = { onCrossEvent(OnDeleteSellersAndShipments(transShipments(it))) },
                onAddShipments = { onCrossEvent(OnSaveSellersAndShipments(transShipments(it)                 )) },
            )
        },
        list = {
            SellersList(
                state = state.listing,
                onDelete = { onEvent(OnDelete(it)) },
                selected = setOf(state.detail.getSuccess()?.first),
                onSelect = { onEvent(OnOpen(it)) },
            )
        },
        snackbarHost = {
            AlarmManagerSnackbar(state = state.snackbar)
            AlarmManagerSnackbar(state = crossState.snackbar)

            AlarmManagerSnackbar(state = state.snackbar)
            AlarmManagerSnackbar(state = citiesState.snackbar)
            AlarmManagerSnackbar(state = salesState.snackbar)
            AlarmManagerSnackbar(state = productsState.snackbar)
            AlarmManagerSnackbar(state = shipmentsState.snackbar)
            AlarmManagerSnackbar(state = crossState.snackbar)
        },
    )
}
