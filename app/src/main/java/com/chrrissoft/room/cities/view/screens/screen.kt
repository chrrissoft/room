package com.chrrissoft.room.cities.view.screens

import androidx.compose.runtime.Composable
import com.chrrissoft.room.carriers.view.events.CarriersEvent
import com.chrrissoft.room.carriers.view.states.CarriersState
import com.chrrissoft.room.cities.db.objects.City
import com.chrrissoft.room.cities.db.objects.CityWithNestedRelationship
import com.chrrissoft.room.cities.view.events.CitiesEvent
import com.chrrissoft.room.cities.view.events.CitiesEvent.OnChange
import com.chrrissoft.room.cities.view.events.CitiesEvent.OnChangePage
import com.chrrissoft.room.cities.view.events.CitiesEvent.OnCreate
import com.chrrissoft.room.cities.view.events.CitiesEvent.OnDelete
import com.chrrissoft.room.cities.view.events.CitiesEvent.OnOpen
import com.chrrissoft.room.cities.view.events.CitiesEvent.OnSave
import com.chrrissoft.room.cities.view.states.CitiesState
import com.chrrissoft.room.cities.view.ui.CitiesList
import com.chrrissoft.room.cities.view.ui.CityWithRelationship
import com.chrrissoft.room.costumers.view.events.CostumersEvent
import com.chrrissoft.room.costumers.view.states.CostumersState
import com.chrrissoft.room.countries.view.states.CountriesState
import com.chrrissoft.room.cross.db.objects.CitiesAndShipments
import com.chrrissoft.room.cross.db.objects.CitiesAndSuppliers
import com.chrrissoft.room.cross.view.events.CrossRefEvent
import com.chrrissoft.room.cross.view.events.CrossRefEvent.OnDeleteCitiesAndShipments
import com.chrrissoft.room.cross.view.events.CrossRefEvent.OnDeleteCitiesAndSuppliers
import com.chrrissoft.room.cross.view.events.CrossRefEvent.OnSaveCitiesAndShipments
import com.chrrissoft.room.cross.view.events.CrossRefEvent.OnSaveCitiesAndSuppliers
import com.chrrissoft.room.cross.view.state.CrossRefState
import com.chrrissoft.room.orders.view.events.OrdersEvent
import com.chrrissoft.room.orders.view.states.OrdersState
import com.chrrissoft.room.sellers.view.events.SellersEvent
import com.chrrissoft.room.sellers.view.states.SellersState
import com.chrrissoft.room.shipments.db.objects.ShippingWithRelationship
import com.chrrissoft.room.shipments.view.states.ShipmentsState
import com.chrrissoft.room.suppliers.db.objects.SupplierWithRelationship
import com.chrrissoft.room.suppliers.view.states.SuppliersState
import com.chrrissoft.room.ui.components.AlarmManagerSnackbar
import com.chrrissoft.room.ui.components.CommonScreen
import com.chrrissoft.room.utils.ResStateUtils.getSuccess
import com.chrrissoft.room.utils.Utils.uuid

@Composable
fun CitiesScreen(
    state: CitiesState,
    carriersState: CarriersState,
    costumersState: CostumersState,
    ordersState: OrdersState,
    sellersState: SellersState,
    shipmentsState: ShipmentsState,
    suppliersState: SuppliersState,
    crossState: CrossRefState,
    onEvent: (CitiesEvent) -> Unit,
    onCarriersEvent: (CarriersEvent) -> Unit,
    onCostumersEvent: (CostumersEvent) -> Unit,
    onOrdersEvent: (OrdersEvent) -> Unit,
    onSellersEvent: (SellersEvent) -> Unit,
    onCrossEvent: (CrossRefEvent) -> Unit,
    countriesState: CountriesState,
    onOpenDrawer: () -> Unit,
) {
    val id = state.detail.getSuccess()?.first
    val transShipments = { data: Map<String, ShippingWithRelationship> ->
        data.mapNotNull { id?.let { id -> CitiesAndShipments(id, it.key) } }
    }

    val transSuppliers = { data: Map<String, SupplierWithRelationship> ->
        data.mapNotNull { id?.let { id -> CitiesAndSuppliers(uuid, id, it.key) } }
    }

    CommonScreen(
        page = state.page,
        title = "Cities",
        onChangePage = { onEvent(OnChangePage(it)) },
        onSave = { state.detail.getSuccess()?.let { onEvent(OnSave(it)) } },
        onCreate = { onEvent(OnCreate(it to CityWithNestedRelationship(City(it)))) },
        onNavigation = onOpenDrawer,
        details = {
            CityWithRelationship(
                state = state.detail,
                onStateChange = { onEvent(OnChange(it)) },
                countries = countriesState.listing,
                carriers = carriersState.listing,
                onRemoveCarriers = null,
                onAddCarriers = { data ->
                    id ?: return@CityWithRelationship
                    data.mapValues { it.value.carrier.copy(cityId = id) }
                        .also { onCarriersEvent(CarriersEvent.OnSaveRaw(it)) }
                },
                costumers = costumersState.listing,
                onRemoveCostumers = { data ->
                    data.mapValues { it.value.costumer.copy(cityId = null) }
                        .also { onCostumersEvent(CostumersEvent.OnSaveRaw(it)) }
                },
                onAddCostumers = { data ->
                    id ?: return@CityWithRelationship
                    data.mapValues { it.value.costumer.copy(cityId = id) }
                        .also { onCostumersEvent(CostumersEvent.OnSaveRaw(it)) }
                },
                orders = ordersState.listing,
                onRemoveOrders = null,
                onAddOrders = { data ->
                    id ?: return@CityWithRelationship
                    data.mapValues { it.value.order.copy(cityId = id) }
                        .also { onOrdersEvent(OrdersEvent.OnSaveRaw(it)) }
                },
                sellers = sellersState.listing,
                onRemoveSellers = null,
                onAddSellers = { data ->
                    id ?: return@CityWithRelationship
                    data.mapValues { it.value.seller.copy(cityId = id) }
                        .also { onSellersEvent(SellersEvent.OnSaveRaw(it)) }
                },
                shipments = shipmentsState.listing,
                onRemoveShipments = { onCrossEvent(OnDeleteCitiesAndShipments(transShipments(it))) },
                onAddShipments = { onCrossEvent(OnSaveCitiesAndShipments(transShipments(it))) },
                suppliers = suppliersState.listing,
                onRemoveSuppliers = { onCrossEvent(OnDeleteCitiesAndSuppliers) },
                onAddSuppliers = { onCrossEvent(OnSaveCitiesAndSuppliers(transSuppliers(it))) },
            )
        },
        list = {
            CitiesList(
                state = state.listing,
                onDelete = { onEvent(OnDelete(it)) },
                selected = setOf(state.detail.getSuccess()?.first),
                onSelect = { onEvent(OnOpen(it)) },
            )
        },
        snackbarHost = {
            AlarmManagerSnackbar(state = state.snackbar)
            AlarmManagerSnackbar(state = carriersState.snackbar)
            AlarmManagerSnackbar(state = costumersState.snackbar)
            AlarmManagerSnackbar(state = ordersState.snackbar)
            AlarmManagerSnackbar(state = sellersState.snackbar)
            AlarmManagerSnackbar(state = shipmentsState.snackbar)
            AlarmManagerSnackbar(state = suppliersState.snackbar)
            AlarmManagerSnackbar(state = crossState.snackbar)
        },
    )
}
