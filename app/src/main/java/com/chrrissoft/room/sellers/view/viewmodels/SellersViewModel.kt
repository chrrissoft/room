package com.chrrissoft.room.sellers.view.viewmodels

import com.chrrissoft.room.base.view.handler.BaseEventHandler
import com.chrrissoft.room.base.view.viewmodel.BaseViewModel
import com.chrrissoft.room.sellers.db.objects.Seller
import com.chrrissoft.room.sellers.db.objects.SellerWithRelationship
import com.chrrissoft.room.sellers.db.usecases.DeleteSellersUseCase
import com.chrrissoft.room.sellers.db.usecases.GetSellersUseCase
import com.chrrissoft.room.sellers.db.usecases.SaveSellersUseCase
import com.chrrissoft.room.sellers.view.events.SellersEvent.OnChange
import com.chrrissoft.room.sellers.view.events.SellersEvent.OnCreate
import com.chrrissoft.room.sellers.view.events.SellersEvent.OnDelete
import com.chrrissoft.room.sellers.view.events.SellersEvent.OnOpen
import com.chrrissoft.room.sellers.view.events.SellersEvent.OnSave
import com.chrrissoft.room.sellers.view.states.SellersState
import com.chrrissoft.room.sellers.view.viewmodels.SellersViewModel.EventHandler
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
class SellersViewModel @Inject constructor(
    private val GetSellersUseCase: GetSellersUseCase,
    private val SaveSellersUseCase: SaveSellersUseCase,
    private val DeleteSellersUseCase: DeleteSellersUseCase,
) : BaseViewModel<EventHandler, SellersState>() {
    override val eventHandler = EventHandler()
    override val _state = MutableStateFlow(SellersState())
    override val stateFlow = _state.asStateFlow()

    init {
        loadSellers()
    }

    inner class EventHandler : BaseEventHandler() {
        fun onEvent(event: OnOpen) = loadSeller(event.data)

        fun onEvent(event: OnSave) = saveSellers(mapOf(event.data))

        fun onEvent(event: OnCreate) = create(event.data)

        fun onEvent(event: OnChange) = updateState(seller = Success(event.data))

        fun onEvent(event: OnDelete) = deleteSellers(event.data.mapValues { it.value.seller })
    }


    private fun create(data: Pair<String, SellerWithRelationship>) {
        (state.seller as? Success)?.data?.let { saveSellers(mapOf(it)) }
        updateState(seller = Success(data))
    }


    private fun saveSellers(data: Map<String, SellerWithRelationship>) {
        updateState(state.sellers.map { it + data })
        saveSellers(data) { updateState() }
    }

    private fun saveSellers(
        data: Map<String, SellerWithRelationship>,
        block: suspend CoroutineScope.(ResState<Any>) -> Unit
    ) = scope.launch { SaveSellersUseCase(data.map { it.value }).collect { block(it) } }


    private fun deleteSellers(data: Map<String, Seller>) {
        updateState(state.sellers.map { it.minus(data.keys) })
        deleteSellers(data) { updateState() }
    }

    private fun deleteSellers(
        data: Map<String, Seller>,
        block: suspend CoroutineScope.(ResState<Any>) -> Unit
    ) = scope.launch { DeleteSellersUseCase(data.map { it.value }).collect { block(it) } }


    private fun loadSellers() = collectSellers { updateState(it) }

    private fun collectSellers(
        block: suspend CoroutineScope.(ResState<Map<String, SellerWithRelationship>>) -> Unit
    ) = scope.launch { GetSellersUseCase().collect { block(it) } }


    private fun loadSeller(id: String) = collectSeller(id) { updateState(seller = it) }

    private fun collectSeller(
        id: String,
        block: suspend CoroutineScope.(ResState<Pair<String, SellerWithRelationship>>) -> Unit
    ) = scope.launch { GetSellersUseCase(id).collect { block(it) } }


    private fun updateState(
        sellers: ResState<Map<String, SellerWithRelationship>> = state.sellers,
        seller: ResState<Pair<String, SellerWithRelationship>> = state.seller,
        snackbar: SnackbarData = state.snackbar,
    ) {
        _state.update { it.copy(seller = seller, sellers = sellers, snackbar = snackbar) }
    }
}
