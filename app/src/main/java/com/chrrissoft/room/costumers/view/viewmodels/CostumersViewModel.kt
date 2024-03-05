package com.chrrissoft.room.costumers.view.viewmodels

import com.chrrissoft.room.base.view.handler.BaseEventHandler
import com.chrrissoft.room.base.view.viewmodel.BaseViewModel
import com.chrrissoft.room.costumers.db.objects.Costumer
import com.chrrissoft.room.costumers.db.objects.CostumerWithRelationship
import com.chrrissoft.room.costumers.db.usecases.DeleteCostumersUseCase
import com.chrrissoft.room.costumers.db.usecases.GetCostumersUseCase
import com.chrrissoft.room.costumers.db.usecases.SaveCostumersUseCase
import com.chrrissoft.room.costumers.view.events.CostumersEvent.OnChange
import com.chrrissoft.room.costumers.view.events.CostumersEvent.OnChangePage
import com.chrrissoft.room.costumers.view.events.CostumersEvent.OnCreate
import com.chrrissoft.room.costumers.view.events.CostumersEvent.OnDelete
import com.chrrissoft.room.costumers.view.events.CostumersEvent.OnOpen
import com.chrrissoft.room.costumers.view.events.CostumersEvent.OnSave
import com.chrrissoft.room.costumers.view.states.CostumersState
import com.chrrissoft.room.costumers.view.viewmodels.CostumersViewModel.EventHandler
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
class CostumersViewModel @Inject constructor(
    private val GetCostumersUseCase: GetCostumersUseCase,
    private val SaveCostumersUseCase: SaveCostumersUseCase,
    private val DeleteCostumersUseCase: DeleteCostumersUseCase,
) : BaseViewModel<EventHandler, CostumersState>() {
    override val eventHandler = EventHandler()
    override val mutableState = MutableStateFlow(CostumersState())
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

    private fun save(data: Map<String, CostumerWithRelationship>) {
        save(data.map { it.value.costumer }) {  }
    }

    private fun open(data: Pair<String, CostumerWithRelationship>) {
        (state.detail as? Success)?.data?.let { save(mapOf(it)) }
        updateState(detail = Success(data), page = DETAIL)
        loadDetail(data.first)
    }

    private fun create(data: Pair<String, CostumerWithRelationship>) {
        detailJob?.cancel()
        (state.detail as? Success)?.data?.let { save(mapOf(it)) }
        updateState(detail = Success(data), page = DETAIL)
    }

    private fun change(data: Pair<String, CostumerWithRelationship>) {
        updateState(detail = Success(data), listing = state.listing.map { it + data })
    }

    private fun delete(data: Map<String, CostumerWithRelationship>) {
        updateState(listing = state.listing.map { it.minus(data.keys) })
        delete(data.map { it.value.costumer }) { }
    }


    private fun save(
        data: List<Costumer>,
        block: suspend CoroutineScope.(ResState<Any>) -> Unit
    ) = scope.launch { SaveCostumersUseCase(data).collect { block(it) } }


    private fun delete(
        data: List<Costumer>,
        block: suspend CoroutineScope.(ResState<Any>) -> Unit
    ) = scope.launch { DeleteCostumersUseCase(data).collect { block(it) } }


    private fun loadData() = collectData { updateState(listing = it) }

    private fun collectData(
        block: suspend CoroutineScope.(ResState<Map<String, CostumerWithRelationship>>) -> Unit
    ) {
        listingJob?.cancel()
        listingJob = scope.launch { GetCostumersUseCase().collect { block(it) } }
    }


    private fun loadDetail(id: String) = collectDetail(id) { updateState(detail = it) }

    private fun collectDetail(
        id: String,
        block: suspend CoroutineScope.(ResState<Pair<String, CostumerWithRelationship>>) -> Unit
    ) {
        detailJob?.cancel()
        detailJob = scope.launch { GetCostumersUseCase(id).collect { block(it) } }
    }


    private fun updateState(
        detail: ResState<Pair<String, CostumerWithRelationship>> = state.detail,
        listing: ResState<Map<String, CostumerWithRelationship>> = state.listing,
        page: Page = state.page,
        snackbar: SnackbarData = state.snackbar,
    ) {
        mutableState.update {
            it.copy(detail = detail, listing = listing, snackbar = snackbar, page = page)
        }
    }
}
