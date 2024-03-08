package com.chrrissoft.room.orders.view.viewmodels

import com.chrrissoft.room.base.view.handler.BaseEventHandler
import com.chrrissoft.room.base.view.viewmodel.BaseViewModel
import com.chrrissoft.room.orders.db.objects.Order
import com.chrrissoft.room.orders.db.objects.OrderWithNestedRelationship
import com.chrrissoft.room.orders.db.objects.OrderWithRelationship
import com.chrrissoft.room.orders.db.usecases.DeleteOrdersUseCase
import com.chrrissoft.room.orders.db.usecases.GetOrdersUseCase
import com.chrrissoft.room.orders.db.usecases.SaveOrdersUseCase
import com.chrrissoft.room.orders.view.events.OrdersEvent.OnChange
import com.chrrissoft.room.orders.view.events.OrdersEvent.OnChangePage
import com.chrrissoft.room.orders.view.events.OrdersEvent.OnCreate
import com.chrrissoft.room.orders.view.events.OrdersEvent.OnDelete
import com.chrrissoft.room.orders.view.events.OrdersEvent.OnOpen
import com.chrrissoft.room.orders.view.events.OrdersEvent.OnSave
import com.chrrissoft.room.orders.view.states.OrdersState
import com.chrrissoft.room.orders.view.viewmodels.OrdersViewModel.EventHandler
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
class OrdersViewModel @Inject constructor(
    private val GetOrdersUseCase: GetOrdersUseCase,
    private val SaveOrdersUseCase: SaveOrdersUseCase,
    private val DeleteOrdersUseCase: DeleteOrdersUseCase,
) : BaseViewModel<EventHandler, OrdersState>() {
    override val eventHandler = EventHandler()
    override val mutableState = MutableStateFlow(OrdersState())
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

    private fun save(data: Map<String, OrderWithNestedRelationship>) {
        save(data.map { it.value.order }) {  }
    }

    private fun open(data: Pair<String, OrderWithRelationship>) {
        (state.detail as? Success)?.data?.let { save(mapOf(it)) }
        updateState(page = DETAIL)
        loadDetail(data.first)
    }

    private fun create(data: Pair<String, OrderWithNestedRelationship>) {
        detailJob?.cancel()
        (state.detail as? Success)?.data?.let { save(mapOf(it)) }
        updateState(detail = Success(data), page = DETAIL)
    }

    private fun change(data: Pair<String, OrderWithNestedRelationship>) {
        updateState(detail = Success(data))
    }

    private fun delete(data: Map<String, OrderWithRelationship>) {
        updateState(listing = state.listing.map { it.minus(data.keys) })
        delete(data.map { it.value.order }) { }
    }


    private fun save(
        data: List<Order>,
        block: suspend CoroutineScope.(ResState<Any>) -> Unit
    ) = scope.launch { SaveOrdersUseCase(data).collect { block(it) } }


    private fun delete(
        data: List<Order>,
        block: suspend CoroutineScope.(ResState<Any>) -> Unit
    ) = scope.launch { DeleteOrdersUseCase(data).collect { block(it) } }


    private fun loadData() = collectData { updateState(listing = it) }

    private fun collectData(
        block: suspend CoroutineScope.(ResState<Map<String, OrderWithRelationship>>) -> Unit
    ) {
        listingJob?.cancel()
        listingJob = scope.launch { GetOrdersUseCase().collect { block(it) } }
    }


    private fun loadDetail(id: String) = collectDetail(id) { updateState(detail = it) }

    private fun collectDetail(
        id: String,
        block: suspend CoroutineScope.(ResState<Pair<String, OrderWithNestedRelationship>>) -> Unit
    ) {
        detailJob?.cancel()
        detailJob = scope.launch { GetOrdersUseCase(id).collect { block(it) } }
    }


    private fun updateState(
        detail: ResState<Pair<String, OrderWithNestedRelationship>> = state.detail,
        listing: ResState<Map<String, OrderWithRelationship>> = state.listing,
        page: Page = state.page,
        snackbar: SnackbarData = state.snackbar,
    ) {
        mutableState.update {
            it.copy(detail = detail, listing = listing, snackbar = snackbar, page = page)
        }
    }
}
