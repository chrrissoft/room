package com.chrrissoft.room.promotions.view.viewmodels

import com.chrrissoft.room.base.view.handler.BaseEventHandler
import com.chrrissoft.room.base.view.viewmodel.BaseViewModel
import com.chrrissoft.room.promotions.db.objects.Promotion
import com.chrrissoft.room.promotions.db.objects.PromotionNestedWithRelationship
import com.chrrissoft.room.promotions.db.objects.PromotionWithRelationship
import com.chrrissoft.room.promotions.db.usecases.DeletePromotionsUseCase
import com.chrrissoft.room.promotions.db.usecases.GetPromotionsUseCase
import com.chrrissoft.room.promotions.db.usecases.SavePromotionsUseCase
import com.chrrissoft.room.promotions.view.events.PromotionsEvent.OnChange
import com.chrrissoft.room.promotions.view.events.PromotionsEvent.OnChangePage
import com.chrrissoft.room.promotions.view.events.PromotionsEvent.OnCreate
import com.chrrissoft.room.promotions.view.events.PromotionsEvent.OnDelete
import com.chrrissoft.room.promotions.view.events.PromotionsEvent.OnOpen
import com.chrrissoft.room.promotions.view.events.PromotionsEvent.OnSave
import com.chrrissoft.room.promotions.view.states.PromotionsState
import com.chrrissoft.room.promotions.view.viewmodels.PromotionsViewModel.EventHandler
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
class PromotionsViewModel @Inject constructor(
    private val GetPromotionsUseCase: GetPromotionsUseCase,
    private val SavePromotionsUseCase: SavePromotionsUseCase,
    private val DeletePromotionsUseCase: DeletePromotionsUseCase,
) : BaseViewModel<EventHandler, PromotionsState>() {
    override val eventHandler = EventHandler()
    override val mutableState = MutableStateFlow(PromotionsState())
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

    private fun save(data: Map<String, PromotionNestedWithRelationship>) {
        save(data.map { it.value.promotion }) {  }
    }

    private fun open(data: Pair<String, PromotionWithRelationship>) {
        (state.detail as? Success)?.data?.let { save(mapOf(it)) }
        updateState(page = DETAIL)
        loadDetail(data.first)
    }

    private fun create(data: Pair<String, PromotionNestedWithRelationship>) {
        detailJob?.cancel()
        (state.detail as? Success)?.data?.let { save(mapOf(it)) }
        updateState(detail = Success(data), page = DETAIL)
    }

    private fun change(data: Pair<String, PromotionNestedWithRelationship>) {
        updateState(detail = Success(data))
    }

    private fun delete(data: Map<String, PromotionWithRelationship>) {
        updateState(listing = state.listing.map { it.minus(data.keys) })
        delete(data.map { it.value.promotion }) { }
    }


    private fun save(
        data: List<Promotion>,
        block: suspend CoroutineScope.(ResState<Any>) -> Unit
    ) = scope.launch { SavePromotionsUseCase(data).collect { block(it) } }


    private fun delete(
        data: List<Promotion>,
        block: suspend CoroutineScope.(ResState<Any>) -> Unit
    ) = scope.launch { DeletePromotionsUseCase(data).collect { block(it) } }


    private fun loadData() = collectData { updateState(listing = it) }

    private fun collectData(
        block: suspend CoroutineScope.(ResState<Map<String, PromotionWithRelationship>>) -> Unit
    ) {
        listingJob?.cancel()
        listingJob = scope.launch { GetPromotionsUseCase().collect { block(it) } }
    }


    private fun loadDetail(id: String) = collectDetail(id) { updateState(detail = it) }

    private fun collectDetail(
        id: String,
        block: suspend CoroutineScope.(ResState<Pair<String, PromotionNestedWithRelationship>>) -> Unit
    ) {
        detailJob?.cancel()
        detailJob = scope.launch { GetPromotionsUseCase(id).collect { block(it) } }
    }


    private fun updateState(
        detail: ResState<Pair<String, PromotionNestedWithRelationship>> = state.detail,
        listing: ResState<Map<String, PromotionWithRelationship>> = state.listing,
        page: Page = state.page,
        snackbar: SnackbarData = state.snackbar,
    ) {
        mutableState.update {
            it.copy(detail = detail, listing = listing, snackbar = snackbar, page = page)
        }
    }
}
