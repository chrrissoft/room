package com.chrrissoft.room.shipments.view.viewmodels

import com.chrrissoft.room.base.view.handler.BaseEventHandler
import com.chrrissoft.room.base.view.viewmodel.BaseViewModel
import com.chrrissoft.room.shipments.db.objects.Shipping
import com.chrrissoft.room.shipments.db.objects.ShippingWithRelationship
import com.chrrissoft.room.shipments.db.usecases.DeleteShipmentsUseCase
import com.chrrissoft.room.shipments.db.usecases.GetShipmentsUseCase
import com.chrrissoft.room.shipments.db.usecases.SaveShipmentsUseCase
import com.chrrissoft.room.shipments.view.events.ShipmentsEvent.OnChange
import com.chrrissoft.room.shipments.view.events.ShipmentsEvent.OnCreate
import com.chrrissoft.room.shipments.view.events.ShipmentsEvent.OnDelete
import com.chrrissoft.room.shipments.view.events.ShipmentsEvent.OnOpen
import com.chrrissoft.room.shipments.view.events.ShipmentsEvent.OnSave
import com.chrrissoft.room.shipments.view.states.ShipmentsState
import com.chrrissoft.room.shipments.view.viewmodels.ShipmentsViewModel.EventHandler
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.app.ResState.Success
import com.chrrissoft.room.shared.view.Page
import com.chrrissoft.room.shipments.view.events.ShipmentsEvent
import com.chrrissoft.room.ui.entities.SnackbarData
import com.chrrissoft.room.utils.ResStateUtils.map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShipmentsViewModel @Inject constructor(
    private val GetShipmentsUseCase: GetShipmentsUseCase,
    private val SaveShipmentsUseCase: SaveShipmentsUseCase,
    private val DeleteShipmentsUseCase: DeleteShipmentsUseCase,
) : BaseViewModel<EventHandler, ShipmentsState>() {
    override val eventHandler = EventHandler()
    override val _state = MutableStateFlow(ShipmentsState())
    override val stateFlow = _state.asStateFlow()

    init {
        loadShipments()
    }

    inner class EventHandler : BaseEventHandler() {
        fun onEvent(event: OnOpen) = loadShipping(event.data)

        fun onEvent(event: OnSave) = saveShipments(mapOf(event.data))

        fun onEvent(event: OnCreate) = create(event.data)

        fun onEvent(event: OnChange) = updateState(shipping = Success(event.data))

        fun onEvent(event: OnDelete) = deleteShipments(event.data.mapValues { it.value.shipping })
        fun onEvent(event: ShipmentsEvent.OnChangePage) = updateState(page = event.data)
    }


    private fun create(data: Pair<String, ShippingWithRelationship>) {
        (state.shipping as? Success)?.data?.let { saveShipments(mapOf(it)) }
        updateState(shipping = Success(data))
    }


    private fun saveShipments(data: Map<String, ShippingWithRelationship>) {
        updateState(state.shipments.map { it + data })
        saveShipments(data) { updateState() }
    }

    private fun saveShipments(
        data: Map<String, ShippingWithRelationship>,
        block: suspend CoroutineScope.(ResState<Any>) -> Unit
    ) = scope.launch { SaveShipmentsUseCase(data.map { it.value }).collect { block(it) } }


    private fun deleteShipments(data: Map<String, Shipping>) {
        updateState(state.shipments.map { it.minus(data.keys) })
        deleteShipments(data) { updateState() }
    }

    private fun deleteShipments(
        data: Map<String, Shipping>,
        block: suspend CoroutineScope.(ResState<Any>) -> Unit
    ) = scope.launch { DeleteShipmentsUseCase(data.map { it.value }).collect { block(it) } }


    private fun loadShipments() = collectShipments { updateState(it) }

    private fun collectShipments(
        block: suspend CoroutineScope.(ResState<Map<String, ShippingWithRelationship>>) -> Unit
    ) = scope.launch { GetShipmentsUseCase().collect { block(it) } }


    private fun loadShipping(id: String) = collectShipping(id) { updateState(shipping = it) }

    private fun collectShipping(
        id: String,
        block: suspend CoroutineScope.(ResState<Pair<String, ShippingWithRelationship>>) -> Unit
    ) = scope.launch { GetShipmentsUseCase(id).collect { block(it) } }


    private fun updateState(
        shipments: ResState<Map<String, ShippingWithRelationship>> = state.shipments,
        shipping: ResState<Pair<String, ShippingWithRelationship>> = state.shipping,
        page: Page = state.page,
        snackbar: SnackbarData = state.snackbar,
    ) {
        _state.update { it.copy(shipping = shipping, shipments = shipments, page = page, snackbar = snackbar) }
    }
}
