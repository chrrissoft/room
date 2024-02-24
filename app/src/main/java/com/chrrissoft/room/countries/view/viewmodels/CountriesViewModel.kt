package com.chrrissoft.room.countries.view.viewmodels

import com.chrrissoft.room.base.view.handler.BaseEventHandler
import com.chrrissoft.room.base.view.viewmodel.BaseViewModel
import com.chrrissoft.room.countries.db.objects.Country
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
    override val _state = MutableStateFlow(CountriesState())
    override val stateFlow = _state.asStateFlow()

    private var listJob: Job? = null
    private var detailJob: Job? = null

    init {
        loadCountries()
    }

    inner class EventHandler : BaseEventHandler() {
        fun onEvent(event: OnOpen) = openCountry(event.data)

        fun onEvent(event: OnSave) = saveCountries(mapOf(event.data))

        fun onEvent(event: OnCreate) = create(event.data)

        fun onEvent(event: OnChange) = updateState(country = Success(event.data))

        fun onEvent(event: OnDelete) = deleteCountries(event.data.mapValues { it.value.country })

        fun onEvent(event: OnChangePage) = updateState(page = event.data)
    }


    private fun create(data: Pair<String, CountryWithRelationship>) {
        (state.country as? Success)?.data?.let { saveCountries(mapOf(it)) }
        updateState(country = Success(data), page = DETAIL)
        (state.country as? Success)?.data?.let { saveCountries(mapOf(it)) }
    }


    private fun saveCountries(data: Map<String, CountryWithRelationship>) {
        updateState(state.countries.map { it + data })
        saveCountries(data) { updateState() }
    }

    private fun saveCountries(
        data: Map<String, CountryWithRelationship>,
        block: suspend CoroutineScope.(ResState<Any>) -> Unit
    ) = scope.launch { SaveCountriesUseCase(data.map { it.value }).collect { block(it) } }


    private fun deleteCountries(data: Map<String, Country>) {
        updateState(state.countries.map { it.minus(data.keys) })
        deleteCountries(data) {  }
    }

    private fun deleteCountries(
        data: Map<String, Country>,
        block: suspend CoroutineScope.(ResState<Any>) -> Unit
    ) = scope.launch { DeleteCountriesUseCase(data.map { it.value }).collect { block(it) } }


    private fun loadCountries() = collectCountries { updateState(it) }

    private fun collectCountries(
        block: suspend CoroutineScope.(ResState<Map<String, CountryWithRelationship>>) -> Unit
    ) {
        listJob?.cancel()
        listJob = scope.launch { GetCountriesUseCase().collect { block(it) } }
    }

    private fun openCountry(id: String) {
        (state.country as? Success)?.data?.let { saveCountries(mapOf(it)) }
        loadCountry(id)
        updateState(page = DETAIL)
    }

    private fun loadCountry(id: String) = collectCountry(id) { updateState(country = it) }

    private fun collectCountry(
        id: String,
        block: suspend CoroutineScope.(ResState<Pair<String, CountryWithRelationship>>) -> Unit
    )  {
        detailJob?.cancel()
        detailJob = scope.launch { GetCountriesUseCase(id).collect { block(it) } }
    }


    private fun updateState(
        countries: ResState<Map<String, CountryWithRelationship>> = state.countries,
        country: ResState<Pair<String, CountryWithRelationship>> = state.country,
        snackbar: SnackbarData = state.snackbar,
        page: Page = state.page,
    ) {
        _state.update { it.copy(country = country, countries = countries, snackbar = snackbar, page = page) }
    }
}
