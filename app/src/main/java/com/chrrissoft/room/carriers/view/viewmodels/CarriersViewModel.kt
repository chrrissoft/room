package com.chrrissoft.room.carriers.view.viewmodels

import com.chrrissoft.room.base.view.handler.BaseEventHandler
import com.chrrissoft.room.base.view.viewmodel.BaseViewModel
import com.chrrissoft.room.carriers.db.objects.Carrier
import com.chrrissoft.room.carriers.db.objects.CarrierWithNestedRelationship
import com.chrrissoft.room.carriers.db.objects.CarrierWithRelationship
import com.chrrissoft.room.carriers.db.usecases.DeleteCarriersUseCase
import com.chrrissoft.room.carriers.db.usecases.GetCarriersUseCase
import com.chrrissoft.room.carriers.db.usecases.SaveCarriersUseCase
import com.chrrissoft.room.carriers.view.events.CarriersEvent.OnChange
import com.chrrissoft.room.carriers.view.events.CarriersEvent.OnChangePage
import com.chrrissoft.room.carriers.view.events.CarriersEvent.OnCreate
import com.chrrissoft.room.carriers.view.events.CarriersEvent.OnDelete
import com.chrrissoft.room.carriers.view.events.CarriersEvent.OnOpen
import com.chrrissoft.room.carriers.view.events.CarriersEvent.OnSave
import com.chrrissoft.room.carriers.view.events.CarriersEvent.OnSaveRaw
import com.chrrissoft.room.carriers.view.states.CarriersState
import com.chrrissoft.room.carriers.view.viewmodels.CarriersViewModel.EventHandler
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.app.ResState.Success
import com.chrrissoft.room.shared.view.Page
import com.chrrissoft.room.shared.view.Page.DETAIL
import com.chrrissoft.room.ui.entities.SnackbarData
import com.chrrissoft.room.utils.ResStateUtils.map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
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
    override val mutableState = MutableStateFlow(CarriersState())
    override val stateFlow = mutableState.asStateFlow()

    private var detailJob: Job? = null
    private var listingJob: Job? = null

    init {
        loadData()
    }

    inner class EventHandler : BaseEventHandler() {
        fun onEvent(event: OnSave) = save(event.data)
        fun onEvent(event: OnOpen) = open(event.data)
        fun onEvent(event: OnCreate) = create(event.data)
        fun onEvent(event: OnChange) = change(event.data)
        fun onEvent(event: OnDelete) = delete(event.data)
        fun onEvent(event: OnChangePage) = updateState(page = event.data)
        fun onEvent(event: OnSaveRaw) = save(event.data.map { it.value }) { showSnackbar(it) }
    }

    private fun save(data: Map<String, CarrierWithNestedRelationship>) =
        save(data.map { it.value.carrier }) { showSnackbar(it) }

    private fun open(data: Pair<String, CarrierWithRelationship>) {
        (state.detail as? Success)?.data?.let { save(mapOf(it)) }
        updateState(page = DETAIL)
        loadDetail(data.first)
    }

    private fun create(data: Pair<String, CarrierWithNestedRelationship>) {
        detailJob?.cancel()
        (state.detail as? Success)?.data?.let { save(mapOf(it)) }
        updateState(detail = Success(data), page = DETAIL)
    }

    private fun change(data: Pair<String, CarrierWithNestedRelationship>) {
        updateState(detail = Success(data))
    }

    private fun delete(data: Map<String, CarrierWithRelationship>) {
        updateState(listing = state.listing.map { it.minus(data.keys) })
        delete(data.map { it.value.carrier }) { }
    }


    private fun save(
        data: List<Carrier>,
        block: suspend CoroutineScope.(ResState<Any>) -> Unit
    ) = scope.launch { SaveCarriersUseCase(data).collect { block(it) } }


    private fun delete(
        data: List<Carrier>,
        block: suspend CoroutineScope.(ResState<Any>) -> Unit
    ) = scope.launch { DeleteCarriersUseCase(data).collect { block(it) } }


    private fun loadData() = collectData { updateState(listing = it) }

    private fun collectData(
        block: suspend CoroutineScope.(ResState<Map<String, CarrierWithRelationship>>) -> Unit
    ) {
        listingJob?.cancel()
        listingJob = scope.launch { GetCarriersUseCase().collect { block(it) } }
    }


    private fun loadDetail(id: String) = collectDetail(id) { updateState(detail = it) }

    private fun collectDetail(
        id: String,
        block: suspend CoroutineScope.(ResState<Pair<String, CarrierWithNestedRelationship>>) -> Unit
    ) {
        detailJob?.cancel()
        detailJob = scope.launch { GetCarriersUseCase(id).collect { block(it) } }
    }


    private fun updateState(
        detail: ResState<Pair<String, CarrierWithNestedRelationship>> = state.detail,
        listing: ResState<Map<String, CarrierWithRelationship>> = state.listing,
        page: Page = state.page,
        snackbar: SnackbarData = state.snackbar,
    ) {
        mutableState.update {
            it.copy(detail = detail, listing = listing, snackbar = snackbar, page = page)
        }
    }

    override fun updateSnackbarType(messageType: SnackbarData.MessageType) =
        updateState(snackbar = state.snackbar.copy(type = messageType))
}
