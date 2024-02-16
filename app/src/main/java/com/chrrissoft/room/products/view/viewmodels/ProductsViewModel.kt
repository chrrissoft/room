package com.chrrissoft.room.products.view.viewmodels

import com.chrrissoft.room.base.view.handler.BaseEventHandler
import com.chrrissoft.room.base.view.viewmodel.BaseViewModel
import com.chrrissoft.room.products.db.objects.Product
import com.chrrissoft.room.products.db.objects.ProductWithRelationship
import com.chrrissoft.room.products.db.usecases.DeleteProductsUseCase
import com.chrrissoft.room.products.db.usecases.GetProductsUseCase
import com.chrrissoft.room.products.db.usecases.SaveProductsUseCase
import com.chrrissoft.room.products.view.events.ProductsEvent.OnChange
import com.chrrissoft.room.products.view.events.ProductsEvent.OnCreate
import com.chrrissoft.room.products.view.events.ProductsEvent.OnDelete
import com.chrrissoft.room.products.view.events.ProductsEvent.OnOpen
import com.chrrissoft.room.products.view.events.ProductsEvent.OnSave
import com.chrrissoft.room.products.view.states.ProductsState
import com.chrrissoft.room.products.view.viewmodels.ProductsViewModel.EventHandler
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
class ProductsViewModel @Inject constructor(
    private val GetProductsUseCase: GetProductsUseCase,
    private val SaveProductsUseCase: SaveProductsUseCase,
    private val DeleteProductsUseCase: DeleteProductsUseCase,
) : BaseViewModel<EventHandler, ProductsState>() {
    override val eventHandler = EventHandler()
    override val _state = MutableStateFlow(ProductsState())
    override val stateFlow = _state.asStateFlow()

    init {
        loadProducts()
    }

    inner class EventHandler : BaseEventHandler() {
        fun onEvent(event: OnOpen) = loadProduct(event.data)

        fun onEvent(event: OnSave) = saveProducts(mapOf(event.data))

        fun onEvent(event: OnCreate) = create(event.data)

        fun onEvent(event: OnChange) = updateState(product = Success(event.data))

        fun onEvent(event: OnDelete) = deleteProducts(event.data.mapValues { it.value.product })
    }


    private fun create(data: Pair<String, ProductWithRelationship>) {
        (state.product as? Success)?.data?.let { saveProducts(mapOf(it)) }
        updateState(product = Success(data))
    }


    private fun saveProducts(data: Map<String, ProductWithRelationship>) {
        updateState(state.products.map { it + data })
        saveProducts(data) { updateState() }
    }

    private fun saveProducts(
        data: Map<String, ProductWithRelationship>,
        block: suspend CoroutineScope.(ResState<Any>) -> Unit
    ) = scope.launch { SaveProductsUseCase(data.map { it.value }).collect { block(it) } }


    private fun deleteProducts(data: Map<String, Product>) {
        updateState(state.products.map { it.minus(data.keys) })
        deleteProducts(data) { updateState() }
    }

    private fun deleteProducts(
        data: Map<String, Product>,
        block: suspend CoroutineScope.(ResState<Any>) -> Unit
    ) = scope.launch { DeleteProductsUseCase(data.map { it.value }).collect { block(it) } }


    private fun loadProducts() = collectProducts { updateState(it) }

    private fun collectProducts(
        block: suspend CoroutineScope.(ResState<Map<String, ProductWithRelationship>>) -> Unit
    ) = scope.launch { GetProductsUseCase().collect { block(it) } }


    private fun loadProduct(id: String) = collectProduct(id) { updateState(product = it) }

    private fun collectProduct(
        id: String,
        block: suspend CoroutineScope.(ResState<Pair<String, ProductWithRelationship>>) -> Unit
    ) = scope.launch { GetProductsUseCase(id).collect { block(it) } }


    private fun updateState(
        products: ResState<Map<String, ProductWithRelationship>> = state.products,
        product: ResState<Pair<String, ProductWithRelationship>> = state.product,
        snackbar: SnackbarData = state.snackbar,
    ) {
        _state.update { it.copy(product = product, products = products, snackbar = snackbar) }
    }
}
