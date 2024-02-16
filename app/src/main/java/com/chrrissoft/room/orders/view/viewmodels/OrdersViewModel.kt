package com.chrrissoft.room.orders.view.viewmodels

import com.chrrissoft.room.base.view.handler.BaseEventHandler
import com.chrrissoft.room.base.view.viewmodel.BaseViewModel
import com.chrrissoft.room.orders.db.objects.Order
import com.chrrissoft.room.orders.db.objects.OrderWithRelationship
import com.chrrissoft.room.orders.db.usecases.DeleteOrdersUseCase
import com.chrrissoft.room.orders.db.usecases.GetOrdersUseCase
import com.chrrissoft.room.orders.db.usecases.SaveOrdersUseCase
import com.chrrissoft.room.orders.view.events.OrdersEvent.OnChange
import com.chrrissoft.room.orders.view.events.OrdersEvent.OnCreate
import com.chrrissoft.room.orders.view.events.OrdersEvent.OnDelete
import com.chrrissoft.room.orders.view.events.OrdersEvent.OnOpen
import com.chrrissoft.room.orders.view.events.OrdersEvent.OnSave
import com.chrrissoft.room.orders.view.states.OrdersState
import com.chrrissoft.room.orders.view.viewmodels.OrdersViewModel.EventHandler
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.app.ResState.Success
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
class OrdersViewModel @Inject constructor(
    private val GetOrdersUseCase: GetOrdersUseCase,
    private val SaveOrdersUseCase: SaveOrdersUseCase,
    private val DeleteOrdersUseCase: DeleteOrdersUseCase,
) : BaseViewModel<EventHandler, OrdersState>() {
    override val eventHandler = EventHandler()
    override val _state = MutableStateFlow(OrdersState())
    override val stateFlow = _state.asStateFlow()

    init {
        loadOrders()
    }

    inner class EventHandler : BaseEventHandler() {
        fun onEvent(event: OnOpen) = loadOrder(event.data)

        fun onEvent(event: OnSave) = saveOrders(mapOf(event.data))

        fun onEvent(event: OnCreate) = create(event.data)

        fun onEvent(event: OnChange) = updateState(order = Success(event.data))

        fun onEvent(event: OnDelete) = deleteOrders(event.data.mapValues { it.value.order })
    }


    private fun create(data: Pair<String, OrderWithRelationship>) {
        (state.order as? Success)?.data?.let { saveOrders(mapOf(it)) }
        updateState(order = Success(data))
    }


    private fun saveOrders(data: Map<String, OrderWithRelationship>) {
        updateState(state.orders.map { it + data })
        saveOrders(data) { updateState() }
    }

    private fun saveOrders(
        data: Map<String, OrderWithRelationship>,
        block: suspend CoroutineScope.(ResState<Any>) -> Unit
    ) = scope.launch { SaveOrdersUseCase(data.map { it.value }).collect { block(it) } }


    private fun deleteOrders(data: Map<String, Order>) {
        updateState(state.orders.map { it.minus(data.keys) })
        deleteOrders(data) { updateState() }
    }

    private fun deleteOrders(
        data: Map<String, Order>,
        block: suspend CoroutineScope.(ResState<Any>) -> Unit
    ) = scope.launch { DeleteOrdersUseCase(data.map { it.value }).collect { block(it) } }


    private fun loadOrders() = collectOrders { updateState(it) }

    private fun collectOrders(
        block: suspend CoroutineScope.(ResState<Map<String, OrderWithRelationship>>) -> Unit
    ) = scope.launch { GetOrdersUseCase().collect { block(it) } }


    private fun loadOrder(id: String) = collectOrder(id) { updateState(order = it) }

    private fun collectOrder(
        id: String,
        block: suspend CoroutineScope.(ResState<Pair<String, OrderWithRelationship>>) -> Unit
    ) = scope.launch { GetOrdersUseCase(id).collect { block(it) } }


    private fun updateState(
        orders: ResState<Map<String, OrderWithRelationship>> = state.orders,
        order: ResState<Pair<String, OrderWithRelationship>> = state.order,
        snackbar: SnackbarData = state.snackbar,
    ) {
        _state.update { it.copy(order = order, orders = orders, snackbar = snackbar) }
    }
}
