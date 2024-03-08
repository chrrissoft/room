package com.chrrissoft.room.cities.view.viewmodels

import com.chrrissoft.room.base.view.handler.BaseEventHandler
import com.chrrissoft.room.base.view.viewmodel.BaseViewModel
import com.chrrissoft.room.cities.db.objects.City
import com.chrrissoft.room.cities.db.objects.CityWithNestedRelationship
import com.chrrissoft.room.cities.db.objects.CityWithRelationship
import com.chrrissoft.room.cities.db.usecases.DeleteCitiesUseCase
import com.chrrissoft.room.cities.db.usecases.GetCitiesUseCase
import com.chrrissoft.room.cities.db.usecases.SaveCitiesUseCase
import com.chrrissoft.room.cities.view.events.CitiesEvent.OnChange
import com.chrrissoft.room.cities.view.events.CitiesEvent.OnChangePage
import com.chrrissoft.room.cities.view.events.CitiesEvent.OnCreate
import com.chrrissoft.room.cities.view.events.CitiesEvent.OnDelete
import com.chrrissoft.room.cities.view.events.CitiesEvent.OnOpen
import com.chrrissoft.room.cities.view.events.CitiesEvent.OnSave
import com.chrrissoft.room.cities.view.states.CitiesState
import com.chrrissoft.room.cities.view.viewmodels.CitiesViewModel.EventHandler
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
class CitiesViewModel @Inject constructor(
    private val GetCitiesUseCase: GetCitiesUseCase,
    private val SaveCitiesUseCase: SaveCitiesUseCase,
    private val DeleteCitiesUseCase: DeleteCitiesUseCase,
) : BaseViewModel<EventHandler, CitiesState>() {
    override val eventHandler = EventHandler()
    override val mutableState = MutableStateFlow(CitiesState())
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

    private fun save(data: Map<String, CityWithNestedRelationship>) {
        save(data.map { it.value.city }) {  }
    }

    private fun open(data: Pair<String, CityWithRelationship>) {
        (state.detail as? Success)?.data?.let { save(mapOf(it)) }
        updateState(page = DETAIL)
        loadDetail(data.first)
    }

    private fun create(data: Pair<String, CityWithNestedRelationship>) {
        detailJob?.cancel()
        (state.detail as? Success)?.data?.let { save(mapOf(it)) }
        updateState(detail = Success(data), page = DETAIL)
    }

    private fun change(data: Pair<String, CityWithNestedRelationship>) {
        updateState(detail = Success(data))
    }

    private fun delete(data: Map<String, CityWithRelationship>) {
        updateState(listing = state.listing.map { it.minus(data.keys) })
        delete(data.map { it.value.city }) { }
    }


    private fun save(
        data: List<City>,
        block: suspend CoroutineScope.(ResState<Any>) -> Unit
    ) = scope.launch { SaveCitiesUseCase(data).collect { block(it) } }


    private fun delete(
        data: List<City>,
        block: suspend CoroutineScope.(ResState<Any>) -> Unit
    ) = scope.launch { DeleteCitiesUseCase(data).collect { block(it) } }


    private fun loadData() = collectData { updateState(listing = it) }

    private fun collectData(
        block: suspend CoroutineScope.(ResState<Map<String, CityWithRelationship>>) -> Unit
    ) {
        listingJob?.cancel()
        listingJob = scope.launch { GetCitiesUseCase().collect { block(it) } }
    }


    private fun loadDetail(id: String) = collectDetail(id) { updateState(detail = it) }

    private fun collectDetail(
        id: String,
        block: suspend CoroutineScope.(ResState<Pair<String, CityWithNestedRelationship>>) -> Unit
    ) {
        detailJob?.cancel()
        detailJob = scope.launch { GetCitiesUseCase(id).collect { block(it) } }
    }


    private fun updateState(
        detail: ResState<Pair<String, CityWithNestedRelationship>> = state.detail,
        listing: ResState<Map<String, CityWithRelationship>> = state.listing,
        page: Page = state.page,
        snackbar: SnackbarData = state.snackbar,
    ) {
        mutableState.update {
            it.copy(detail = detail, listing = listing, snackbar = snackbar, page = page)
        }
    }
}
