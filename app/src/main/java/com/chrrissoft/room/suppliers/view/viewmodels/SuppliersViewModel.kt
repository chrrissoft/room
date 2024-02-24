package com.chrrissoft.room.suppliers.view.viewmodels

import com.chrrissoft.room.base.view.handler.BaseEventHandler
import com.chrrissoft.room.base.view.viewmodel.BaseViewModel
import com.chrrissoft.room.suppliers.db.objects.Supplier
import com.chrrissoft.room.suppliers.db.objects.SupplierWithRelationship
import com.chrrissoft.room.suppliers.db.usecases.DeleteSuppliersUseCase
import com.chrrissoft.room.suppliers.db.usecases.GetSuppliersUseCase
import com.chrrissoft.room.suppliers.db.usecases.SaveSuppliersUseCase
import com.chrrissoft.room.suppliers.view.events.SuppliersEvent.OnChange
import com.chrrissoft.room.suppliers.view.events.SuppliersEvent.OnCreate
import com.chrrissoft.room.suppliers.view.events.SuppliersEvent.OnDelete
import com.chrrissoft.room.suppliers.view.events.SuppliersEvent.OnOpen
import com.chrrissoft.room.suppliers.view.events.SuppliersEvent.OnSave
import com.chrrissoft.room.suppliers.view.states.SuppliersState
import com.chrrissoft.room.suppliers.view.viewmodels.SuppliersViewModel.EventHandler
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.app.ResState.Success
import com.chrrissoft.room.shared.view.Page
import com.chrrissoft.room.suppliers.view.events.SuppliersEvent
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
class SuppliersViewModel @Inject constructor(
    private val GetSuppliersUseCase: GetSuppliersUseCase,
    private val SaveSuppliersUseCase: SaveSuppliersUseCase,
    private val DeleteSuppliersUseCase: DeleteSuppliersUseCase,
) : BaseViewModel<EventHandler, SuppliersState>() {
    override val eventHandler = EventHandler()
    override val _state = MutableStateFlow(SuppliersState())
    override val stateFlow = _state.asStateFlow()

    init {
        loadSuppliers()
    }

    inner class EventHandler : BaseEventHandler() {
        fun onEvent(event: OnOpen) = openSupplier(event.data)

        fun onEvent(event: OnSave) = saveSuppliers(mapOf(event.data))

        fun onEvent(event: OnCreate) = create(event.data)

        fun onEvent(event: OnChange) = updateState(supplier = Success(event.data))

        fun onEvent(event: OnDelete) = deleteSuppliers(event.data.mapValues { it.value.supplier })
        fun onEvent(event: SuppliersEvent.OnChangePage) = updateState(page = event.data)
    }


    private fun create(data: Pair<String, SupplierWithRelationship>) {
        (state.supplier as? Success)?.data?.let { saveSuppliers(mapOf(it)) }
        updateState(supplier = Success(data), page = Page.DETAIL)
    }


    private fun saveSuppliers(data: Map<String, SupplierWithRelationship>) {
        updateState(state.suppliers.map { it + data })
        saveSuppliers(data) { updateState() }
    }

    private fun saveSuppliers(
        data: Map<String, SupplierWithRelationship>,
        block: suspend CoroutineScope.(ResState<Any>) -> Unit
    ) = scope.launch { SaveSuppliersUseCase(data.map { it.value }).collect { block(it) } }


    private fun deleteSuppliers(data: Map<String, Supplier>) {
        updateState(state.suppliers.map { it.minus(data.keys) })
        deleteSuppliers(data) { updateState() }
    }

    private fun deleteSuppliers(
        data: Map<String, Supplier>,
        block: suspend CoroutineScope.(ResState<Any>) -> Unit
    ) = scope.launch { DeleteSuppliersUseCase(data.map { it.value }).collect { block(it) } }


    private fun loadSuppliers() = collectSuppliers { updateState(it) }

    private fun collectSuppliers(
        block: suspend CoroutineScope.(ResState<Map<String, SupplierWithRelationship>>) -> Unit
    ) = scope.launch { GetSuppliersUseCase().collect { block(it) } }


    private fun openSupplier(id: String) {
        updateState(page = Page.DETAIL)
        loadSupplier(id)
    }

    private fun loadSupplier(id: String) = collectSupplier(id) { updateState(supplier = it) }

    private fun collectSupplier(
        id: String,
        block: suspend CoroutineScope.(ResState<Pair<String, SupplierWithRelationship>>) -> Unit
    ) = scope.launch { GetSuppliersUseCase(id).collect { block(it) } }


    private fun updateState(
        suppliers: ResState<Map<String, SupplierWithRelationship>> = state.suppliers,
        supplier: ResState<Pair<String, SupplierWithRelationship>> = state.supplier,
        page: Page = state.page,
        snackbar: SnackbarData = state.snackbar,
    ) {
        _state.update { it.copy(supplier = supplier, suppliers = suppliers, page = page, snackbar = snackbar) }
    }
}
