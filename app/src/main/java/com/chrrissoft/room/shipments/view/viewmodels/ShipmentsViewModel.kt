package com.chrrissoft.room.shipments.view.viewmodels

import com.chrrissoft.room.base.view.handler.BaseEventHandler
import com.chrrissoft.room.base.view.viewmodel.BaseViewModel
import com.chrrissoft.room.shipments.db.objects.Shipping
import com.chrrissoft.room.shipments.db.objects.ShippingWithRelationship
import com.chrrissoft.room.shipments.db.usecases.DeleteShipmentsUseCase
import com.chrrissoft.room.shipments.db.usecases.GetShipmentsUseCase
import com.chrrissoft.room.shipments.db.usecases.SaveShipmentsUseCase
import com.chrrissoft.room.shipments.view.events.ShipmentsEvent.OnChange
import com.chrrissoft.room.shipments.view.events.ShipmentsEvent.OnChangePage
import com.chrrissoft.room.shipments.view.events.ShipmentsEvent.OnCreate
import com.chrrissoft.room.shipments.view.events.ShipmentsEvent.OnDelete
import com.chrrissoft.room.shipments.view.events.ShipmentsEvent.OnOpen
import com.chrrissoft.room.shipments.view.events.ShipmentsEvent.OnSave
import com.chrrissoft.room.shipments.view.states.ShipmentsState
import com.chrrissoft.room.shipments.view.viewmodels.ShipmentsViewModel.EventHandler
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
class ShipmentsViewModel @Inject constructor(
    private val GetShipmentsUseCase: GetShipmentsUseCase,
    private val SaveShipmentsUseCase: SaveShipmentsUseCase,
    private val DeleteShipmentsUseCase: DeleteShipmentsUseCase,
) : BaseViewModel<EventHandler, ShipmentsState>() {
    override val eventHandler = EventHandler()
    override val mutableState = MutableStateFlow(ShipmentsState())
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
    }

    private fun save(data: Map<String, ShippingWithRelationship>) {
        save(data.map { it.value.shipping }) {  }
    }

    private fun open(data: Pair<String, ShippingWithRelationship>) {
        (state.detail as? Success)?.data?.let { save(mapOf(it)) }
        updateState(detail = Success(data), page = DETAIL)
        loadDetail(data.first)
    }

    private fun create(data: Pair<String, ShippingWithRelationship>) {
        detailJob?.cancel()
        (state.detail as? Success)?.data?.let { save(mapOf(it)) }
        updateState(detail = Success(data), page = DETAIL)
    }

    private fun change(data: Pair<String, ShippingWithRelationship>) {
        updateState(detail = Success(data), listing = state.listing.map { it + data })
    }

    private fun delete(data: Map<String, ShippingWithRelationship>) {
        updateState(listing = state.listing.map { it.minus(data.keys) })
        delete(data.map { it.value.shipping }) { }
    }


    private fun save(
        data: List<Shipping>,
        block: suspend CoroutineScope.(ResState<Any>) -> Unit
    ) = scope.launch { SaveShipmentsUseCase(data).collect { block(it) } }


    private fun delete(
        data: List<Shipping>,
        block: suspend CoroutineScope.(ResState<Any>) -> Unit
    ) = scope.launch { DeleteShipmentsUseCase(data).collect { block(it) } }


    private fun loadData() = collectData { updateState(listing = it) }

    private fun collectData(
        block: suspend CoroutineScope.(ResState<Map<String, ShippingWithRelationship>>) -> Unit
    ) {
        listingJob?.cancel()
        listingJob = scope.launch { GetShipmentsUseCase().collect { block(it) } }
    }


    private fun loadDetail(id: String) = collectDetail(id) { updateState(detail = it) }

    private fun collectDetail(
        id: String,
        block: suspend CoroutineScope.(ResState<Pair<String, ShippingWithRelationship>>) -> Unit
    ) {
        detailJob?.cancel()
        detailJob = scope.launch { GetShipmentsUseCase(id).collect { block(it) } }
    }


    private fun updateState(
        detail: ResState<Pair<String, ShippingWithRelationship>> = state.detail,
        listing: ResState<Map<String, ShippingWithRelationship>> = state.listing,
        page: Page = state.page,
        snackbar: SnackbarData = state.snackbar,
    ) {
        mutableState.update {
            it.copy(detail = detail, listing = listing, snackbar = snackbar, page = page)
        }
    }
}
