package com.chrrissoft.room.shipments.view.screens

import androidx.compose.runtime.Composable
import com.chrrissoft.room.carriers.view.states.CarriersState
import com.chrrissoft.room.cities.db.objects.CityWithRelationship
import com.chrrissoft.room.cities.view.states.CitiesState
import com.chrrissoft.room.costumers.db.objects.CostumerWithRelationship
import com.chrrissoft.room.costumers.view.states.CostumersState
import com.chrrissoft.room.cross.db.objects.CitiesAndShipments
import com.chrrissoft.room.cross.db.objects.CostumersAndShipments
import com.chrrissoft.room.cross.db.objects.SellersAndShipments
import com.chrrissoft.room.cross.view.events.CrossRefEvent
import com.chrrissoft.room.cross.view.events.CrossRefEvent.OnDeleteCitiesAndShipments
import com.chrrissoft.room.cross.view.events.CrossRefEvent.OnDeleteCostumersAndShipments
import com.chrrissoft.room.cross.view.events.CrossRefEvent.OnDeleteSellersAndShipments
import com.chrrissoft.room.cross.view.events.CrossRefEvent.OnSaveCitiesAndShipments
import com.chrrissoft.room.cross.view.events.CrossRefEvent.OnSaveCostumersAndShipments
import com.chrrissoft.room.cross.view.events.CrossRefEvent.OnSaveSellersAndShipments
import com.chrrissoft.room.cross.view.state.CrossRefState
import com.chrrissoft.room.orders.view.events.OrdersEvent
import com.chrrissoft.room.orders.view.states.OrdersState
import com.chrrissoft.room.sellers.db.objects.SellerWithRelationship
import com.chrrissoft.room.sellers.view.states.SellersState
import com.chrrissoft.room.shipments.db.objects.Shipping
import com.chrrissoft.room.shipments.db.objects.ShippingWithNestedRelationship
import com.chrrissoft.room.shipments.view.events.ShipmentsEvent
import com.chrrissoft.room.shipments.view.events.ShipmentsEvent.OnChange
import com.chrrissoft.room.shipments.view.events.ShipmentsEvent.OnChangePage
import com.chrrissoft.room.shipments.view.events.ShipmentsEvent.OnCreate
import com.chrrissoft.room.shipments.view.events.ShipmentsEvent.OnDelete
import com.chrrissoft.room.shipments.view.events.ShipmentsEvent.OnOpen
import com.chrrissoft.room.shipments.view.events.ShipmentsEvent.OnSave
import com.chrrissoft.room.shipments.view.states.ShipmentsState
import com.chrrissoft.room.shipments.view.ui.ShipmentsList
import com.chrrissoft.room.shipments.view.ui.ShippingWithRelationship
import com.chrrissoft.room.ui.components.AlarmManagerSnackbar
import com.chrrissoft.room.ui.components.CommonScreen
import com.chrrissoft.room.utils.ResStateUtils.getSuccess

@Composable
fun ShipmentsScreen(
    state: ShipmentsState,
    carriersState: CarriersState,
    ordersState: OrdersState,
    citiesState: CitiesState,
    costumersState: CostumersState,
    sellersState: SellersState,
    crossState: CrossRefState,
    onEvent: (ShipmentsEvent) -> Unit,
    onOrdersEvent: (OrdersEvent) -> Unit,
    onCrossEvent: (CrossRefEvent) -> Unit,
    onOpenDrawer: () -> Unit,
) {
    val id = state.detail.getSuccess()?.first
    val transformCities = { data: Map<String, CityWithRelationship> ->
        data.mapNotNull { id?.let { id -> CitiesAndShipments(it.key, id) } }
    }
    val transformCostumers = { data: Map<String, CostumerWithRelationship> ->
        data.mapNotNull { id?.let { id -> CostumersAndShipments(it.key, id) } }
    }
    val transformSellers = { data: Map<String, SellerWithRelationship> ->
        data.mapNotNull { id?.let { id -> SellersAndShipments(it.key, id) } }
    }

    CommonScreen(
        page = state.page,
        title = "Shipments",
        onChangePage = { onEvent(OnChangePage(it)) },
        onSave = { state.detail.getSuccess()?.let { onEvent(OnSave(it)) } },
        onCreate = { onEvent(OnCreate(it to ShippingWithNestedRelationship(Shipping(it)))) },
        onNavigation = onOpenDrawer,
        details = {
            ShippingWithRelationship(
                state = state.detail,
                onStateChange = { onEvent(OnChange(it)) },
                carriers = carriersState.listing,
                orders = ordersState.listing,
                onRemoveOrders = { data ->
                    data.mapValues { it.value.order.copy(shippingId = null) }
                        .also { onOrdersEvent(OrdersEvent.OnSaveRaw(it)) }
                },
                onAddOrders = { data ->
                    data.mapValues { it.value.order.copy(shippingId = id) }
                        .also { onOrdersEvent(OrdersEvent.OnSaveRaw(it)) }
                },
                cities = citiesState.listing,
                onRemoveCities = {
                    onCrossEvent(OnDeleteCitiesAndShipments(transformCities(it)))
                },
                onAddCities = {
                    onCrossEvent(OnSaveCitiesAndShipments(transformCities(it)))
                },
                costumers = costumersState.listing,
                onRemoveCostumers = {
                    onCrossEvent(OnDeleteCostumersAndShipments(transformCostumers(it)))
                },
                onAddCostumers = {
                    onCrossEvent(OnSaveCostumersAndShipments(transformCostumers(it)))
                },
                sellers = sellersState.listing,
                onRemoveSellers = {
                    onCrossEvent(OnDeleteSellersAndShipments(transformSellers(it)))
                },
                onAddSellers = {
                    onCrossEvent(OnSaveSellersAndShipments(transformSellers(it)))
                },
            )
        },
        list = {
            ShipmentsList(
                state = state.listing,
                onDelete = { onEvent(OnDelete(it)) },
                selected = setOf(state.detail.getSuccess()?.first),
                onSelect = { onEvent(OnOpen(it)) },
            )
        },
        snackbarHost = {
            AlarmManagerSnackbar(state = state.snackbar)
            AlarmManagerSnackbar(state = carriersState.snackbar)
            AlarmManagerSnackbar(state = ordersState.snackbar)
            AlarmManagerSnackbar(state = citiesState.snackbar)
            AlarmManagerSnackbar(state = costumersState.snackbar)
            AlarmManagerSnackbar(state = sellersState.snackbar)
            AlarmManagerSnackbar(state = crossState.snackbar)
        },
    )
}
