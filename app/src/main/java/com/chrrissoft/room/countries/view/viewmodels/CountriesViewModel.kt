package com.chrrissoft.room.countries.view.viewmodels

import com.chrrissoft.room.base.view.handler.BaseEventHandler
import com.chrrissoft.room.base.view.viewmodel.BaseViewModel
import com.chrrissoft.room.countries.db.objects.Country
import com.chrrissoft.room.countries.db.objects.CountryNestedWithRelationship
import com.chrrissoft.room.countries.db.objects.CountryWithRelationship
import com.chrrissoft.room.countries.db.usecases.DeleteCountriesUseCase
import com.chrrissoft.room.countries.db.usecases.GetCountriesUseCase
import com.chrrissoft.room.countries.db.usecases.SaveCountriesUseCase
import com.chrrissoft.room.countries.view.events.CountriesEvent.OnChange
import com.chrrissoft.room.countries.view.events.CountriesEvent.OnChangePage
import com.chrrissoft.room.countries.view.events.CountriesEvent.OnCreate
import com.chrrissoft.room.countries.view.events.CountriesEvent.OnDelete
import com.chrrissoft.room.countries.view.events.CountriesEvent.OnOpen
import com.chrrissoft.room.countries.view.events.CountriesEvent.OnSave
import com.chrrissoft.room.countries.view.states.CountriesState
import com.chrrissoft.room.countries.view.viewmodels.CountriesViewModel.EventHandler
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
class CountriesViewModel @Inject constructor(
    private val GetCountriesUseCase: GetCountriesUseCase,
    private val SaveCountriesUseCase: SaveCountriesUseCase,
    private val DeleteCountriesUseCase: DeleteCountriesUseCase,
) : BaseViewModel<EventHandler, CountriesState>() {
    override val eventHandler = EventHandler()
    override val mutableState = MutableStateFlow(CountriesState())
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

    private fun save(data: Map<String, CountryNestedWithRelationship>) {
        save(data.map { it.value.country }) {  }
    }

    private fun open(data: Pair<String, CountryWithRelationship>) {
        (state.detail as? Success)?.data?.let { save(mapOf(it)) }
        updateState(page = DETAIL)
        loadDetail(data.first)
    }

    private fun create(data: Pair<String, CountryNestedWithRelationship>) {
        detailJob?.cancel()
        (state.detail as? Success)?.data?.let { save(mapOf(it)) }
        updateState(detail = Success(data), page = DETAIL)
    }

    private fun change(data: Pair<String, CountryNestedWithRelationship>) {
        updateState(detail = Success(data))
    }

    private fun delete(data: Map<String, CountryWithRelationship>) {
        updateState(listing = state.listing.map { it.minus(data.keys) })
        delete(data.map { it.value.country }) { }
    }


    private fun save(
        data: List<Country>,
        block: suspend CoroutineScope.(ResState<Any>) -> Unit
    ) = scope.launch { SaveCountriesUseCase(data).collect { block(it) } }


    private fun delete(
        data: List<Country>,
        block: suspend CoroutineScope.(ResState<Any>) -> Unit
    ) = scope.launch { DeleteCountriesUseCase(data).collect { block(it) } }


    private fun loadData() = collectData { updateState(listing = it) }

    private fun collectData(
        block: suspend CoroutineScope.(ResState<Map<String, CountryWithRelationship>>) -> Unit
    ) {
        listingJob?.cancel()
        listingJob = scope.launch { GetCountriesUseCase().collect { block(it) } }
    }


    private fun loadDetail(id: String) = collectDetail(id) { updateState(detail = it) }

    private fun collectDetail(
        id: String,
        block: suspend CoroutineScope.(ResState<Pair<String, CountryNestedWithRelationship>>) -> Unit
    ) {
        detailJob?.cancel()
        detailJob = scope.launch { GetCountriesUseCase(id).collect { block(it) } }
    }


    private fun updateState(
        detail: ResState<Pair<String, CountryNestedWithRelationship>> = state.detail,
        listing: ResState<Map<String, CountryWithRelationship>> = state.listing,
        page: Page = state.page,
        snackbar: SnackbarData = state.snackbar,
    ) {
        mutableState.update {
            it.copy(detail = detail, listing = listing, snackbar = snackbar, page = page)
        }
    }
}
