package com.chrrissoft.room.sellers.view.viewmodels

import com.chrrissoft.room.base.view.handler.BaseEventHandler
import com.chrrissoft.room.base.view.viewmodel.BaseViewModel
import com.chrrissoft.room.sellers.db.objects.Seller
import com.chrrissoft.room.sellers.db.objects.SellerWithRelationship
import com.chrrissoft.room.sellers.db.usecases.DeleteSellersUseCase
import com.chrrissoft.room.sellers.db.usecases.GetSellersUseCase
import com.chrrissoft.room.sellers.db.usecases.SaveSellersUseCase
import com.chrrissoft.room.sellers.view.events.SellersEvent.OnChange
import com.chrrissoft.room.sellers.view.events.SellersEvent.OnChangePage
import com.chrrissoft.room.sellers.view.events.SellersEvent.OnCreate
import com.chrrissoft.room.sellers.view.events.SellersEvent.OnDelete
import com.chrrissoft.room.sellers.view.events.SellersEvent.OnOpen
import com.chrrissoft.room.sellers.view.events.SellersEvent.OnSave
import com.chrrissoft.room.sellers.view.states.SellersState
import com.chrrissoft.room.sellers.view.viewmodels.SellersViewModel.EventHandler
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
class SellersViewModel @Inject constructor(
    private val GetSellersUseCase: GetSellersUseCase,
    private val SaveSellersUseCase: SaveSellersUseCase,
    private val DeleteSellersUseCase: DeleteSellersUseCase,
) : BaseViewModel<EventHandler, SellersState>() {
    override val eventHandler = EventHandler()
    override val mutableState = MutableStateFlow(SellersState())
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

    private fun save(data: Map<String, SellerWithRelationship>) {
        save(data.map { it.value.seller }) {  }
    }

    private fun open(data: Pair<String, SellerWithRelationship>) {
        (state.detail as? Success)?.data?.let { save(mapOf(it)) }
        updateState(detail = Success(data), page = DETAIL)
        loadDetail(data.first)
    }

    private fun create(data: Pair<String, SellerWithRelationship>) {
        detailJob?.cancel()
        (state.detail as? Success)?.data?.let { save(mapOf(it)) }
        updateState(detail = Success(data), page = DETAIL)
    }

    private fun change(data: Pair<String, SellerWithRelationship>) {
        updateState(detail = Success(data), listing = state.listing.map { it + data })
    }

    private fun delete(data: Map<String, SellerWithRelationship>) {
        updateState(listing = state.listing.map { it.minus(data.keys) })
        delete(data.map { it.value.seller }) { }
    }


    private fun save(
        data: List<Seller>,
        block: suspend CoroutineScope.(ResState<Any>) -> Unit
    ) = scope.launch { SaveSellersUseCase(data).collect { block(it) } }


    private fun delete(
        data: List<Seller>,
        block: suspend CoroutineScope.(ResState<Any>) -> Unit
    ) = scope.launch { DeleteSellersUseCase(data).collect { block(it) } }


    private fun loadData() = collectData { updateState(listing = it) }

    private fun collectData(
        block: suspend CoroutineScope.(ResState<Map<String, SellerWithRelationship>>) -> Unit
    ) {
        listingJob?.cancel()
        listingJob = scope.launch { GetSellersUseCase().collect { block(it) } }
    }


    private fun loadDetail(id: String) = collectDetail(id) { updateState(detail = it) }

    private fun collectDetail(
        id: String,
        block: suspend CoroutineScope.(ResState<Pair<String, SellerWithRelationship>>) -> Unit
    ) {
        detailJob?.cancel()
        detailJob = scope.launch { GetSellersUseCase(id).collect { block(it) } }
    }


    private fun updateState(
        detail: ResState<Pair<String, SellerWithRelationship>> = state.detail,
        listing: ResState<Map<String, SellerWithRelationship>> = state.listing,
        page: Page = state.page,
        snackbar: SnackbarData = state.snackbar,
    ) {
        mutableState.update {
            it.copy(detail = detail, listing = listing, snackbar = snackbar, page = page)
        }
    }
}
