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
    override val _state = MutableStateFlow(CostumersState())
    override val stateFlow = _state.asStateFlow()

    private var listJob: Job? = null
    private var detailJob: Job? = null

    init {
        loadCostumers()
    }

    inner class EventHandler : BaseEventHandler() {
        fun onEvent(event: OnOpen) = openCostumer(event.data)

        fun onEvent(event: OnSave) = saveCostumers(mapOf(event.data))

        fun onEvent(event: OnCreate) = create(event.data)

        fun onEvent(event: OnChange) = updateState(costumer = Success(event.data))

        fun onEvent(event: OnDelete) = deleteCostumers(event.data.mapValues { it.value.costumer })
        fun onEvent(event: OnChangePage) = updateState(page = event.data)
    }


    private fun create(data: Pair<String, CostumerWithRelationship>) {
        (state.costumer as? Success)?.data?.let { saveCostumers(mapOf(it)) }
        updateState(costumer = Success(data), page = Page.DETAIL)
    }


    private fun saveCostumers(data: Map<String, CostumerWithRelationship>) {
        updateState(state.costumers.map { it + data })
        saveCostumers(data) { updateState() }
    }

    private fun saveCostumers(
        data: Map<String, CostumerWithRelationship>,
        block: suspend CoroutineScope.(ResState<Any>) -> Unit
    ) = scope.launch { SaveCostumersUseCase(data.map { it.value }).collect { block(it) } }


    private fun deleteCostumers(data: Map<String, Costumer>) {
        updateState(state.costumers.map { it.minus(data.keys) })
        deleteCostumers(data) { updateState() }
    }

    private fun deleteCostumers(
        data: Map<String, Costumer>,
        block: suspend CoroutineScope.(ResState<Any>) -> Unit
    ) = scope.launch { DeleteCostumersUseCase(data.map { it.value }).collect { block(it) } }


    private fun loadCostumers() = collectCostumers { updateState(it) }

    private fun collectCostumers(
        block: suspend CoroutineScope.(ResState<Map<String, CostumerWithRelationship>>) -> Unit
    ) {
        listJob?.cancel()
        listJob = scope.launch { GetCostumersUseCase().collect { block(it) } }
    }

    private fun openCostumer(id: String) {
        (state.costumer as? Success)?.data?.let { saveCostumers(mapOf(it)) }
        updateState(page = Page.DETAIL)
        loadCostumer(id)
    }

    private fun loadCostumer(id: String) = collectCostumer(id) { updateState(costumer = it) }

    private fun collectCostumer(
        id: String,
        block: suspend CoroutineScope.(ResState<Pair<String, CostumerWithRelationship>>) -> Unit
    ) {
        detailJob?.cancel()
        detailJob = scope.launch { GetCostumersUseCase(id).collect { block(it) } }
    }


    private fun updateState(
        costumers: ResState<Map<String, CostumerWithRelationship>> = state.costumers,
        costumer: ResState<Pair<String, CostumerWithRelationship>> = state.costumer,
        page: Page = state.page,
        snackbar: SnackbarData = state.snackbar,
    ) {
        _state.update {
            it.copy(
                costumer = costumer,
                costumers = costumers,
                page = page,
                snackbar = snackbar
            )
        }
    }
}
