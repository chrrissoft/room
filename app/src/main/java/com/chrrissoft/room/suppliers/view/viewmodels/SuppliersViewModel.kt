package com.chrrissoft.room.suppliers.view.viewmodels

import com.chrrissoft.room.base.view.handler.BaseEventHandler
import com.chrrissoft.room.base.view.viewmodel.BaseViewModel
import com.chrrissoft.room.suppliers.db.objects.Supplier
import com.chrrissoft.room.suppliers.db.objects.SupplierWithRelationship
import com.chrrissoft.room.suppliers.db.usecases.DeleteSuppliersUseCase
import com.chrrissoft.room.suppliers.db.usecases.GetSuppliersUseCase
import com.chrrissoft.room.suppliers.db.usecases.SaveSuppliersUseCase
import com.chrrissoft.room.suppliers.view.events.SuppliersEvent.OnChange
import com.chrrissoft.room.suppliers.view.events.SuppliersEvent.OnChangePage
import com.chrrissoft.room.suppliers.view.events.SuppliersEvent.OnCreate
import com.chrrissoft.room.suppliers.view.events.SuppliersEvent.OnDelete
import com.chrrissoft.room.suppliers.view.events.SuppliersEvent.OnOpen
import com.chrrissoft.room.suppliers.view.events.SuppliersEvent.OnSave
import com.chrrissoft.room.suppliers.view.states.SuppliersState
import com.chrrissoft.room.suppliers.view.viewmodels.SuppliersViewModel.EventHandler
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.app.ResState.Success
import com.chrrissoft.room.shared.view.Page
import com.chrrissoft.room.shared.view.Page.DETAIL
import com.chrrissoft.room.suppliers.db.objects.SupplierWithNestedRelationship
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
class SuppliersViewModel @Inject constructor(
    private val GetSuppliersUseCase: GetSuppliersUseCase,
    private val SaveSuppliersUseCase: SaveSuppliersUseCase,
    private val DeleteSuppliersUseCase: DeleteSuppliersUseCase,
) : BaseViewModel<EventHandler, SuppliersState>() {
    override val eventHandler = EventHandler()
    override val mutableState = MutableStateFlow(SuppliersState())
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

    private fun save(data: Map<String, SupplierWithNestedRelationship>) {
        save(data.map { it.value.supplier }) {  }
    }

    private fun open(data: Pair<String, SupplierWithRelationship>) {
        (state.detail as? Success)?.data?.let { save(mapOf(it)) }
        updateState(page = DETAIL)
        loadDetail(data.first)
    }

    private fun create(data: Pair<String, SupplierWithNestedRelationship>) {
        detailJob?.cancel()
        (state.detail as? Success)?.data?.let { save(mapOf(it)) }
        updateState(detail = Success(data), page = DETAIL)
    }

    private fun change(data: Pair<String, SupplierWithNestedRelationship>) {
        updateState(detail = Success(data))
    }

    private fun delete(data: Map<String, SupplierWithRelationship>) {
        updateState(listing = state.listing.map { it.minus(data.keys) })
        delete(data.map { it.value.supplier }) { }
    }


    private fun save(
        data: List<Supplier>,
        block: suspend CoroutineScope.(ResState<Any>) -> Unit
    ) = scope.launch { SaveSuppliersUseCase(data).collect { block(it) } }


    private fun delete(
        data: List<Supplier>,
        block: suspend CoroutineScope.(ResState<Any>) -> Unit
    ) = scope.launch { DeleteSuppliersUseCase(data).collect { block(it) } }


    private fun loadData() = collectData { updateState(listing = it) }

    private fun collectData(
        block: suspend CoroutineScope.(ResState<Map<String, SupplierWithRelationship>>) -> Unit
    ) {
        listingJob?.cancel()
        listingJob = scope.launch { GetSuppliersUseCase().collect { block(it) } }
    }


    private fun loadDetail(id: String) = collectDetail(id) { updateState(detail = it) }

    private fun collectDetail(
        id: String,
        block: suspend CoroutineScope.(ResState<Pair<String, SupplierWithNestedRelationship>>) -> Unit
    ) {
        detailJob?.cancel()
        detailJob = scope.launch { GetSuppliersUseCase(id).collect { block(it) } }
    }


    private fun updateState(
        detail: ResState<Pair<String, SupplierWithNestedRelationship>> = state.detail,
        listing: ResState<Map<String, SupplierWithRelationship>> = state.listing,
        page: Page = state.page,
        snackbar: SnackbarData = state.snackbar,
    ) {
        mutableState.update {
            it.copy(detail = detail, listing = listing, snackbar = snackbar, page = page)
        }
    }
}
