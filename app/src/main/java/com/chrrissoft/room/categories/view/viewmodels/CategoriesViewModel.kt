package com.chrrissoft.room.categories.view.viewmodels

import com.chrrissoft.room.base.view.handler.BaseEventHandler
import com.chrrissoft.room.base.view.viewmodel.BaseViewModel
import com.chrrissoft.room.categories.db.objects.Category
import com.chrrissoft.room.categories.db.objects.CategoryWithNestedRelationship
import com.chrrissoft.room.categories.db.objects.CategoryWithRelationship
import com.chrrissoft.room.categories.db.usecases.DeleteCategoriesUseCase
import com.chrrissoft.room.categories.db.usecases.GetCategoriesUseCase
import com.chrrissoft.room.categories.db.usecases.SaveCategoriesUseCase
import com.chrrissoft.room.categories.view.events.CategoriesEvent.OnChange
import com.chrrissoft.room.categories.view.events.CategoriesEvent.OnChangePage
import com.chrrissoft.room.categories.view.events.CategoriesEvent.OnCreate
import com.chrrissoft.room.categories.view.events.CategoriesEvent.OnDelete
import com.chrrissoft.room.categories.view.events.CategoriesEvent.OnOpen
import com.chrrissoft.room.categories.view.events.CategoriesEvent.OnSave
import com.chrrissoft.room.categories.view.states.CategoriesState
import com.chrrissoft.room.categories.view.viewmodels.CategoriesViewModel.EventHandler
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.app.ResState.Success
import com.chrrissoft.room.shared.view.Page
import com.chrrissoft.room.shared.view.Page.DETAIL
import com.chrrissoft.room.ui.entities.SnackbarData
import com.chrrissoft.room.ui.entities.SnackbarData.MessageType
import com.chrrissoft.room.utils.ResStateUtils.map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val GetCategoriesUseCase: GetCategoriesUseCase,
    private val SaveCategoriesUseCase: SaveCategoriesUseCase,
    private val DeleteCategoriesUseCase: DeleteCategoriesUseCase,
) : BaseViewModel<EventHandler, CategoriesState>() {
    override val eventHandler = EventHandler()
    override val mutableState = MutableStateFlow(CategoriesState())
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

    private fun save(data: Map<String, CategoryWithNestedRelationship>) =
        save(data.map { it.value.category }) { showSnackbar(it) }

    private fun open(data: Pair<String, CategoryWithRelationship>) {
        (state.detail as? Success)?.data?.let { save(mapOf(it)) }
        updateState(page = DETAIL).also { loadDetail(data.first) }
    }

    private fun create(data: Pair<String, CategoryWithNestedRelationship>) {
        detailJob?.cancel()
        (state.detail as? Success)?.data?.let { save(mapOf(it)) }
        updateState(detail = Success(data), page = DETAIL)
    }

    private fun change(data: Pair<String, CategoryWithNestedRelationship>) {
        updateState(detail = Success(data))
    }

    private fun delete(data: Map<String, CategoryWithRelationship>) {
        updateState(listing = state.listing.map { it.minus(data.keys) })
        delete(data.map { it.value.category }) { }
    }


    private fun save(
        data: List<Category>,
        block: suspend CoroutineScope.(ResState<Any>) -> Unit
    ) = launch { SaveCategoriesUseCase(data).collect { block(it) } }


    private fun delete(
        data: List<Category>,
        block: suspend CoroutineScope.(ResState<Any>) -> Unit
    ) = launch { DeleteCategoriesUseCase(data).collect { block(it) } }


    private fun loadData() = collectData { updateState(listing = it) }

    private fun collectData(
        block: suspend CoroutineScope.(ResState<Map<String, CategoryWithRelationship>>) -> Unit
    ) {
        listingJob?.cancel()
        listingJob = launch { GetCategoriesUseCase().collect { block(it) } }
    }


    private fun loadDetail(id: String) = collectDetail(id) { updateState(detail = it) }

    private fun collectDetail(
        id: String,
        block: suspend CoroutineScope.(ResState<Pair<String, CategoryWithNestedRelationship>>) -> Unit
    ) {
        detailJob?.cancel()
        detailJob = launch { GetCategoriesUseCase(id).collect { block(it) } }
    }


    private fun updateState(
        detail: ResState<Pair<String, CategoryWithNestedRelationship>> = state.detail,
        listing: ResState<Map<String, CategoryWithRelationship>> = state.listing,
        page: Page = state.page,
        snackbar: SnackbarData = state.snackbar,
    ) {
        mutableState.update {
            it.copy(detail = detail, listing = listing, snackbar = snackbar, page = page)
        }
    }

    override fun updateSnackbarType(messageType: MessageType) =
        updateState(snackbar = state.snackbar.copy(type = messageType))
}
