package com.chrrissoft.room.sales.view.viewmodels

import com.chrrissoft.room.base.view.handler.BaseEventHandler
import com.chrrissoft.room.base.view.viewmodel.BaseViewModel
import com.chrrissoft.room.sales.db.objects.Sale
import com.chrrissoft.room.sales.db.objects.SaleWithRelationship
import com.chrrissoft.room.sales.db.usecases.DeleteSalesUseCase
import com.chrrissoft.room.sales.db.usecases.GetSalesUseCase
import com.chrrissoft.room.sales.db.usecases.SaveSalesUseCase
import com.chrrissoft.room.sales.view.events.SalesEvent
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
class SalesViewModel @Inject constructor(
    private val GetSalesUseCase: GetSalesUseCase,
    private val SaveSalesUseCase: SaveSalesUseCase,
    private val DeleteSalesUseCase: DeleteSalesUseCase,
) : BaseViewModel<EventHandler, SalesState>() {
    override val eventHandler = EventHandler()
    override val _state = MutableStateFlow(SalesState())
    override val stateFlow = _state.asStateFlow()

    init {
        loadSales()
    }

    inner class EventHandler : BaseEventHandler() {
        fun onEvent(event: OnOpen) = openSale(event.data)

        fun onEvent(event: OnSave) = saveSales(mapOf(event.data))

        fun onEvent(event: OnCreate) = create(event.data)

        fun onEvent(event: OnChange) = updateState(sale = Success(event.data))

        fun onEvent(event: OnDelete) = deleteSales(event.data.mapValues { it.value.sale })

        fun onEvent(event: OnChangePage) = updateState(page = event.data)
    }


    private fun create(data: Pair<String, SaleWithRelationship>) {
        (state.sale as? Success)?.data?.let { saveSales(mapOf(it)) }
        updateState(sale = Success(data), page = Page.DETAIL)
    }


    private fun saveSales(data: Map<String, SaleWithRelationship>) {
        updateState(state.sales.map { it + data })
        saveSales(data) { updateState() }
    }

    private fun saveSales(
        data: Map<String, SaleWithRelationship>,
        block: suspend CoroutineScope.(ResState<Any>) -> Unit
    ) = scope.launch { SaveSalesUseCase(data.map { it.value }).collect { block(it) } }


    private fun deleteSales(data: Map<String, Sale>) {
        updateState(state.sales.map { it.minus(data.keys) })
        deleteSales(data) { updateState() }
    }

    private fun deleteSales(
        data: Map<String, Sale>,
        block: suspend CoroutineScope.(ResState<Any>) -> Unit
    ) = scope.launch { DeleteSalesUseCase(data.map { it.value }).collect { block(it) } }


    private fun loadSales() = collectSales { updateState(it) }

    private fun collectSales(
        block: suspend CoroutineScope.(ResState<Map<String, SaleWithRelationship>>) -> Unit
    ) = scope.launch { GetSalesUseCase().collect { block(it) } }


    private fun openSale(id: String) {
        updateState(page = Page.DETAIL)
        loadSale(id)
    }

    private fun loadSale(id: String) = collectSale(id) { updateState(sale = it) }

    private fun collectSale(
        id: String,
        block: suspend CoroutineScope.(ResState<Pair<String, SaleWithRelationship>>) -> Unit
    ) = scope.launch { GetSalesUseCase(id).collect { block(it) } }


    private fun updateState(
        sales: ResState<Map<String, SaleWithRelationship>> = state.sales,
        sale: ResState<Pair<String, SaleWithRelationship>> = state.sale,
        page: Page = state.page,
        snackbar: SnackbarData = state.snackbar,
    ) {
        _state.update { it.copy(sale = sale, sales = sales, page = page, snackbar = snackbar) }
    }
}
