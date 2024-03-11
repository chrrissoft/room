package com.chrrissoft.room.costumers.view.screens

import androidx.compose.runtime.Composable
import com.chrrissoft.room.cities.view.states.CitiesState
import com.chrrissoft.room.costumers.db.objects.Costumer
import com.chrrissoft.room.costumers.db.objects.CostumerWithNestedRelationship
import com.chrrissoft.room.costumers.view.events.CostumersEvent
import com.chrrissoft.room.costumers.view.events.CostumersEvent.OnChange
import com.chrrissoft.room.costumers.view.events.CostumersEvent.OnChangePage
import com.chrrissoft.room.costumers.view.events.CostumersEvent.OnCreate
import com.chrrissoft.room.costumers.view.events.CostumersEvent.OnDelete
import com.chrrissoft.room.costumers.view.events.CostumersEvent.OnOpen
import com.chrrissoft.room.costumers.view.events.CostumersEvent.OnSave
import com.chrrissoft.room.costumers.view.states.CostumersState
import com.chrrissoft.room.costumers.view.ui.CostumerWithRelationship
import com.chrrissoft.room.costumers.view.ui.CostumersList
import com.chrrissoft.room.cross.db.objects.CostumersAndProducts
import com.chrrissoft.room.cross.db.objects.CostumersAndShipments
import com.chrrissoft.room.cross.view.events.CrossRefEvent
import com.chrrissoft.room.cross.view.events.CrossRefEvent.OnDeleteCostumersAndProducts
import com.chrrissoft.room.cross.view.events.CrossRefEvent.OnDeleteCostumersAndShipments
import com.chrrissoft.room.cross.view.events.CrossRefEvent.OnSaveCostumersAndProducts
import com.chrrissoft.room.cross.view.events.CrossRefEvent.OnSaveCostumersAndShipments
import com.chrrissoft.room.cross.view.state.CrossRefState
import com.chrrissoft.room.products.db.objects.ProductWithRelationship
import com.chrrissoft.room.products.view.states.ProductsState
import com.chrrissoft.room.sales.view.events.SalesEvent
import com.chrrissoft.room.sales.view.states.SalesState
import com.chrrissoft.room.shipments.db.objects.ShippingWithRelationship
import com.chrrissoft.room.shipments.view.states.ShipmentsState
import com.chrrissoft.room.ui.components.AlarmManagerSnackbar
import com.chrrissoft.room.ui.components.CommonScreen
import com.chrrissoft.room.utils.ResStateUtils.getSuccess
import com.chrrissoft.room.utils.Utils.uuid

@Composable
fun CostumersScreen(
    state: CostumersState,
    citiesState: CitiesState,
    salesState: SalesState,
    productsState: ProductsState,
    shipmentsState: ShipmentsState,
    crossState: CrossRefState,
    onSalesEvent: (SalesEvent) -> Unit,
    onCrossEvent: (CrossRefEvent) -> Unit,
    onEvent: (CostumersEvent) -> Unit,
    onOpenDrawer: () -> Unit,
) {
    val id = state.detail.getSuccess()?.first
    val transformShipments = { data: Map<String, ShippingWithRelationship> ->
        data.mapNotNull { id?.let { id -> CostumersAndShipments(id, it.key) } }
    }

    val transformProducts = { data: Map<String, ProductWithRelationship> ->
        data.mapNotNull { id?.let { id -> CostumersAndProducts(uuid, id, it.key) } }
    }

    CommonScreen(
        page = state.page,
        title = "Costumers",
        onChangePage = { onEvent(OnChangePage(it)) },
        onSave = { state.detail.getSuccess()?.let { onEvent(OnSave(it)) } },
        onCreate = { onEvent(OnCreate(it to CostumerWithNestedRelationship(Costumer(it)))) },
        onNavigation = onOpenDrawer,
        details = {
            CostumerWithRelationship(
                state = state.detail,
                onStateChange = { onEvent(OnChange(it)) },
                sales = salesState.listing,
                onRemoveSales = null,
                onAddSales = { data ->
                    id ?: return@CostumerWithRelationship
                    data.mapValues { it.value.sale.copy(costumerId = id) }
                        .also { onSalesEvent(SalesEvent.OnSaveRaw(it)) }
                },
                products = productsState.listing,
                onRemoveProducts = { onCrossEvent(OnDeleteCostumersAndProducts) },
                onAddProducts = {
                    onCrossEvent(OnSaveCostumersAndProducts(transformProducts(it)))
                },
                shipments = shipmentsState.listing,
                onRemoveShipments = {
                    onCrossEvent(OnDeleteCostumersAndShipments(transformShipments(it)))
                },
                onAddShipments = {
                    onCrossEvent(OnSaveCostumersAndShipments(transformShipments(it)))
                },
                cities = citiesState.listing,
            )
        },
        list = {
            CostumersList(
                state = state.listing,
                onDelete = { onEvent(OnDelete(it)) },
                selected = setOf(state.detail.getSuccess()?.first),
                onSelect = { onEvent(OnOpen(it)) },
            )
        },
        snackbarHost = {
            AlarmManagerSnackbar(state = state.snackbar)
            AlarmManagerSnackbar(state = citiesState.snackbar)
            AlarmManagerSnackbar(state = salesState.snackbar)
            AlarmManagerSnackbar(state = productsState.snackbar)
            AlarmManagerSnackbar(state = shipmentsState.snackbar)
            AlarmManagerSnackbar(state = crossState.snackbar)
        },
    )
}
