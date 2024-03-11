package com.chrrissoft.room.products.view.viewmodels

import com.chrrissoft.room.base.view.handler.BaseEventHandler
import com.chrrissoft.room.base.view.viewmodel.BaseViewModel
import com.chrrissoft.room.products.db.objects.Product
import com.chrrissoft.room.products.db.objects.ProductWithNestedRelationship
import com.chrrissoft.room.products.db.objects.ProductWithRelationship
import com.chrrissoft.room.products.db.usecases.DeleteProductsUseCase
import com.chrrissoft.room.products.db.usecases.GetProductsUseCase
import com.chrrissoft.room.products.db.usecases.SaveProductsUseCase
import com.chrrissoft.room.products.view.events.ProductsEvent.OnChange
import com.chrrissoft.room.products.view.events.ProductsEvent.OnChangePage
import com.chrrissoft.room.products.view.events.ProductsEvent.OnCreate
import com.chrrissoft.room.products.view.events.ProductsEvent.OnDelete
import com.chrrissoft.room.products.view.events.ProductsEvent.OnOpen
import com.chrrissoft.room.products.view.events.ProductsEvent.OnSave
import com.chrrissoft.room.products.view.events.ProductsEvent.OnSaveRaw
import com.chrrissoft.room.products.view.states.ProductsState
import com.chrrissoft.room.products.view.viewmodels.ProductsViewModel.EventHandler
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
class ProductsViewModel @Inject constructor(
    private val GetProductsUseCase: GetProductsUseCase,
    private val SaveProductsUseCase: SaveProductsUseCase,
    private val DeleteProductsUseCase: DeleteProductsUseCase,
) : BaseViewModel<EventHandler, ProductsState>() {
    override val eventHandler = EventHandler()
    override val mutableState = MutableStateFlow(ProductsState())
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
        fun onEvent(event: OnSaveRaw) = save(event.data.values.toList()) { showSnackbar(it) }
    }

    private fun save(data: Map<String, ProductWithNestedRelationship>) =
        save(data.map { it.value.product }) { showSnackbar(it) }

    private fun open(data: Pair<String, ProductWithRelationship>) {
        (state.detail as? Success)?.data?.let { save(mapOf(it)) }
        updateState(page = DETAIL)
        loadDetail(data.first)
    }

    private fun create(data: Pair<String, ProductWithNestedRelationship>) {
        detailJob?.cancel()
        (state.detail as? Success)?.data?.let { save(mapOf(it)) }
        updateState(detail = Success(data), page = DETAIL)
    }

    private fun change(data: Pair<String, ProductWithNestedRelationship>) {
        updateState(detail = Success(data))
    }

    private fun delete(data: Map<String, ProductWithRelationship>) {
        updateState(listing = state.listing.map { it.minus(data.keys) })
        delete(data.map { it.value.product }) { }
    }


    private fun save(
        data: List<Product>,
        block: suspend CoroutineScope.(ResState<Any>) -> Unit
    ) = scope.launch { SaveProductsUseCase(data).collect { block(it) } }


    private fun delete(
        data: List<Product>,
        block: suspend CoroutineScope.(ResState<Any>) -> Unit
    ) = scope.launch { DeleteProductsUseCase(data).collect { block(it) } }


    private fun loadData() = collectData { updateState(listing = it) }

    private fun collectData(
        block: suspend CoroutineScope.(ResState<Map<String, ProductWithRelationship>>) -> Unit
    ) {
        listingJob?.cancel()
        listingJob = scope.launch { GetProductsUseCase().collect { block(it) } }
    }


    private fun loadDetail(id: String) = collectDetail(id) { updateState(detail = it) }

    private fun collectDetail(
        id: String,
        block: suspend CoroutineScope.(ResState<Pair<String, ProductWithNestedRelationship>>) -> Unit
    ) {
        detailJob?.cancel()
        detailJob = scope.launch { GetProductsUseCase(id).collect { block(it) } }
    }


    private fun updateState(
        detail: ResState<Pair<String, ProductWithNestedRelationship>> = state.detail,
        listing: ResState<Map<String, ProductWithRelationship>> = state.listing,
        page: Page = state.page,
        snackbar: SnackbarData = state.snackbar,
    ) {
        mutableState.update {
            it.copy(detail = detail, listing = listing, snackbar = snackbar, page = page)
        }
    }

    override fun updateSnackbarType(messageType: SnackbarData.MessageType) =
        updateState(snackbar = state.snackbar.copy(type = messageType))
}
