package com.chrrissoft.room.sales.view.viewmodels

import com.chrrissoft.room.base.view.handler.BaseEventHandler
import com.chrrissoft.room.base.view.viewmodel.BaseViewModel
import com.chrrissoft.room.sales.db.objects.Sale
import com.chrrissoft.room.sales.db.objects.SaleWithNestedRelationship
import com.chrrissoft.room.sales.db.objects.SaleWithRelationship
import com.chrrissoft.room.sales.db.usecases.DeleteSalesUseCase
import com.chrrissoft.room.sales.db.usecases.GetSalesUseCase
import com.chrrissoft.room.sales.db.usecases.SaveSalesUseCase
import com.chrrissoft.room.sales.view.events.SalesEvent.OnChange
import com.chrrissoft.room.sales.view.events.SalesEvent.OnChangePage
import com.chrrissoft.room.sales.view.events.SalesEvent.OnCreate
import com.chrrissoft.room.sales.view.events.SalesEvent.OnDelete
import com.chrrissoft.room.sales.view.events.SalesEvent.OnOpen
import com.chrrissoft.room.sales.view.events.SalesEvent.OnSave
import com.chrrissoft.room.sales.view.states.SalesState
import com.chrrissoft.room.sales.view.viewmodels.SalesViewModel.EventHandler
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
class SalesViewModel @Inject constructor(
    private val GetSalesUseCase: GetSalesUseCase,
    private val SaveSalesUseCase: SaveSalesUseCase,
    private val DeleteSalesUseCase: DeleteSalesUseCase,
) : BaseViewModel<EventHandler, SalesState>() {
    override val eventHandler = EventHandler()
    override val mutableState = MutableStateFlow(SalesState())
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

    private fun save(data: Map<String, SaleWithNestedRelationship>) {
        save(data.map { it.value.sale }) {  }
    }

    private fun open(data: Pair<String, SaleWithRelationship>) {
        (state.detail as? Success)?.data?.let { save(mapOf(it)) }
        updateState(page = DETAIL)
        loadDetail(data.first)
    }

    private fun create(data: Pair<String, SaleWithNestedRelationship>) {
        detailJob?.cancel()
        (state.detail as? Success)?.data?.let { save(mapOf(it)) }
        updateState(detail = Success(data), page = DETAIL)
    }

    private fun change(data: Pair<String, SaleWithNestedRelationship>) {
        updateState(detail = Success(data))
    }

    private fun delete(data: Map<String, SaleWithRelationship>) {
        updateState(listing = state.listing.map { it.minus(data.keys) })
        delete(data.map { it.value.sale }) { }
    }


    private fun save(
        data: List<Sale>,
        block: suspend CoroutineScope.(ResState<Any>) -> Unit
    ) = scope.launch { SaveSalesUseCase(data).collect { block(it) } }


    private fun delete(
        data: List<Sale>,
        block: suspend CoroutineScope.(ResState<Any>) -> Unit
    ) = scope.launch { DeleteSalesUseCase(data).collect { block(it) } }


    private fun loadData() = collectData { updateState(listing = it) }

    private fun collectData(
        block: suspend CoroutineScope.(ResState<Map<String, SaleWithRelationship>>) -> Unit
    ) {
        listingJob?.cancel()
        listingJob = scope.launch { GetSalesUseCase().collect { block(it) } }
    }


    private fun loadDetail(id: String) = collectDetail(id) { updateState(detail = it) }

    private fun collectDetail(
        id: String,
        block: suspend CoroutineScope.(ResState<Pair<String, SaleWithNestedRelationship>>) -> Unit
    ) {
        detailJob?.cancel()
        detailJob = scope.launch { GetSalesUseCase(id).collect { block(it) } }
    }


    private fun updateState(
        detail: ResState<Pair<String, SaleWithNestedRelationship>> = state.detail,
        listing: ResState<Map<String, SaleWithRelationship>> = state.listing,
        page: Page = state.page,
        snackbar: SnackbarData = state.snackbar,
    ) {
        mutableState.update {
            it.copy(detail = detail, listing = listing, snackbar = snackbar, page = page)
        }
    }
}
