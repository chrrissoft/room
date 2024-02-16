package com.chrrissoft.room.inventories.view.viewmodels

import com.chrrissoft.room.base.view.handler.BaseEventHandler
import com.chrrissoft.room.base.view.viewmodel.BaseViewModel
import com.chrrissoft.room.inventories.db.objects.Inventory
import com.chrrissoft.room.inventories.db.objects.InventoryWithRelationship
import com.chrrissoft.room.inventories.db.usecases.DeleteInventoriesUseCase
import com.chrrissoft.room.inventories.db.usecases.GetInventoriesUseCase
import com.chrrissoft.room.inventories.db.usecases.SaveInventoriesUseCase
import com.chrrissoft.room.inventories.view.events.InventoriesEvent.OnChange
import com.chrrissoft.room.inventories.view.events.InventoriesEvent.OnCreate
import com.chrrissoft.room.inventories.view.events.InventoriesEvent.OnDelete
import com.chrrissoft.room.inventories.view.events.InventoriesEvent.OnOpen
import com.chrrissoft.room.inventories.view.events.InventoriesEvent.OnSave
import com.chrrissoft.room.inventories.view.states.InventoriesState
import com.chrrissoft.room.inventories.view.viewmodels.InventoriesViewModel.EventHandler
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
class InventoriesViewModel @Inject constructor(
    private val GetInventoriesUseCase: GetInventoriesUseCase,
    private val SaveInventoriesUseCase: SaveInventoriesUseCase,
    private val DeleteInventoriesUseCase: DeleteInventoriesUseCase,
) : BaseViewModel<EventHandler, InventoriesState>() {
    override val eventHandler = EventHandler()
    override val _state = MutableStateFlow(InventoriesState())
    override val stateFlow = _state.asStateFlow()

    init {
        loadInventories()
    }

    inner class EventHandler : BaseEventHandler() {
        fun onEvent(event: OnOpen) = loadInventory(event.data)

        fun onEvent(event: OnSave) = saveInventories(mapOf(event.data))

        fun onEvent(event: OnCreate) = create(event.data)

        fun onEvent(event: OnChange) = updateState(inventory = Success(event.data))

        fun onEvent(event: OnDelete) = deleteInventories(event.data.mapValues { it.value.inventory })
    }


    private fun create(data: Pair<String, InventoryWithRelationship>) {
        (state.inventory as? Success)?.data?.let { saveInventories(mapOf(it)) }
        updateState(inventory = Success(data))
    }


    private fun saveInventories(data: Map<String, InventoryWithRelationship>) {
        updateState(state.inventories.map { it + data })
        saveInventories(data) { updateState() }
    }

    private fun saveInventories(
        data: Map<String, InventoryWithRelationship>,
        block: suspend CoroutineScope.(ResState<Any>) -> Unit
    ) = scope.launch { SaveInventoriesUseCase(data.map { it.value }).collect { block(it) } }


    private fun deleteInventories(data: Map<String, Inventory>) {
        updateState(state.inventories.map { it.minus(data.keys) })
        deleteInventories(data) { updateState() }
    }

    private fun deleteInventories(
        data: Map<String, Inventory>,
        block: suspend CoroutineScope.(ResState<Any>) -> Unit
    ) = scope.launch { DeleteInventoriesUseCase(data.map { it.value }).collect { block(it) } }


    private fun loadInventories() = collectInventories { updateState(it) }

    private fun collectInventories(
        block: suspend CoroutineScope.(ResState<Map<String, InventoryWithRelationship>>) -> Unit
    ) = scope.launch { GetInventoriesUseCase().collect { block(it) } }


    private fun loadInventory(id: String) = collectInventory(id) { updateState(inventory = it) }

    private fun collectInventory(
        id: String,
        block: suspend CoroutineScope.(ResState<Pair<String, InventoryWithRelationship>>) -> Unit
    ) = scope.launch { GetInventoriesUseCase(id).collect { block(it) } }


    private fun updateState(
        inventories: ResState<Map<String, InventoryWithRelationship>> = state.inventories,
        inventory: ResState<Pair<String, InventoryWithRelationship>> = state.inventory,
        snackbar: SnackbarData = state.snackbar,
    ) {
        _state.update { it.copy(inventory = inventory, inventories = inventories, snackbar = snackbar) }
    }
}
