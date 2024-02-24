package com.chrrissoft.room.carriers.view.viewmodels

import com.chrrissoft.room.base.view.handler.BaseEventHandler
import com.chrrissoft.room.base.view.viewmodel.BaseViewModel
import com.chrrissoft.room.carriers.db.objects.Carrier
import com.chrrissoft.room.carriers.db.objects.CarrierWithRelationship
import com.chrrissoft.room.carriers.db.usecases.DeleteCarriersUseCase
import com.chrrissoft.room.carriers.db.usecases.GetCarriersUseCase
import com.chrrissoft.room.carriers.db.usecases.SaveCarriersUseCase
import com.chrrissoft.room.carriers.view.events.CarriersEvent
import com.chrrissoft.room.carriers.view.events.CarriersEvent.OnChange
import com.chrrissoft.room.carriers.view.events.CarriersEvent.OnCreate
import com.chrrissoft.room.carriers.view.events.CarriersEvent.OnDelete
import com.chrrissoft.room.carriers.view.events.CarriersEvent.OnOpen
import com.chrrissoft.room.carriers.view.events.CarriersEvent.OnSave
import com.chrrissoft.room.carriers.view.states.CarriersState
import com.chrrissoft.room.carriers.view.viewmodels.CarriersViewModel.EventHandler
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.app.ResState.Success
import com.chrrissoft.room.shared.view.Page
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
class CarriersViewModel @Inject constructor(
    private val GetCarriersUseCase: GetCarriersUseCase,
    private val SaveCarriersUseCase: SaveCarriersUseCase,
    private val DeleteCarriersUseCase: DeleteCarriersUseCase,
) : BaseViewModel<EventHandler, CarriersState>() {
    override val eventHandler = EventHandler()
    override val _state = MutableStateFlow(CarriersState())
    override val stateFlow = _state.asStateFlow()

    init {
        loadCarriers()
    }

    inner class EventHandler : BaseEventHandler() {
        fun onEvent(event: OnOpen) = openCarrier(event.data)

        fun onEvent(event: OnSave) = saveCarriers(mapOf(event.data))

        fun onEvent(event: OnCreate) = create(event.data)

        fun onEvent(event: OnChange) = updateState(carrier = Success(event.data))

        fun onEvent(event: OnDelete) = deleteCarriers(event.data.mapValues { it.value.carrier })
        fun onEvent(event: CarriersEvent.OnChangePage) = updateState(page = event.data)
    }


    private fun create(data: Pair<String, CarrierWithRelationship>) {
        (state.carrier as? Success)?.data?.let { saveCarriers(mapOf(it)) }
        updateState(carrier = Success(data), page = Page.DETAIL)
    }


    private fun saveCarriers(data: Map<String, CarrierWithRelationship>) {
        updateState(carriers = state.carriers.map { it + data })
        saveCarriers(data) { updateState() }
    }

    private fun saveCarriers(
        data: Map<String, CarrierWithRelationship>,
        block: suspend CoroutineScope.(ResState<Any>) -> Unit
    ) = scope.launch { SaveCarriersUseCase(data.map { it.value }).collect { block(it) } }


    private fun deleteCarriers(data: Map<String, Carrier>) {
        updateState(carriers = state.carriers.map { it.minus(data.keys) })
        deleteCarriers(data) { updateState() }
    }

    private fun deleteCarriers(
        data: Map<String, Carrier>,
        block: suspend CoroutineScope.(ResState<Any>) -> Unit
    ) = scope.launch { DeleteCarriersUseCase(data.map { it.value }).collect { block(it) } }


    private fun loadCarriers() = collectCarriers { updateState(carriers = it) }

    private fun collectCarriers(
        block: suspend CoroutineScope.(ResState<Map<String, CarrierWithRelationship>>) -> Unit
    ) = scope.launch { GetCarriersUseCase().collect { block(it) } }


    private fun openCarrier(id: String) {
        updateState(page = Page.DETAIL)
        loadCarrier(id)
    }

    private fun loadCarrier(id: String) = collectCarrier(id) { updateState(it) }

    private fun collectCarrier(
        id: String,
        block: suspend CoroutineScope.(ResState<Pair<String, CarrierWithRelationship>>) -> Unit
    ) = scope.launch { GetCarriersUseCase(id).collect { block(it) } }


    private fun updateState(
        carrier: ResState<Pair<String, CarrierWithRelationship>> = state.carrier,
        carriers: ResState<Map<String, CarrierWithRelationship>> = state.carriers,
        page:Page = state.page,
        snackbar: SnackbarData = state.snackbar,
    ) {
        _state.update { it.copy(carrier = carrier, carriers = carriers, page = page, snackbar = snackbar) }
    }
}
