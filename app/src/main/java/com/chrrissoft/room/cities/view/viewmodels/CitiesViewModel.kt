package com.chrrissoft.room.cities.view.viewmodels

import com.chrrissoft.room.base.view.handler.BaseEventHandler
import com.chrrissoft.room.base.view.viewmodel.BaseViewModel
import com.chrrissoft.room.cities.db.objects.City
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
    override val _state = MutableStateFlow(CitiesState())
    override val stateFlow = _state.asStateFlow()

    private var listJob: Job? = null
    private var detailJob: Job? = null

    init {
        loadCities()
    }

    inner class EventHandler : BaseEventHandler() {
        fun onEvent(event: OnOpen) = openCity(event.data)

        fun onEvent(event: OnSave) = saveCities(mapOf(event.data))

        fun onEvent(event: OnCreate) = create(event.data)

        fun onEvent(event: OnChange) = updateState(category = Success(event.data))

        fun onEvent(event: OnDelete) = deleteCities(event.data.mapValues { it.value.city })
        fun onEvent(event: OnChangePage) = updateState(page = event.data)
    }


    private fun create(data: Pair<String, CityWithRelationship>) {
        (state.city as? Success)?.data?.let { saveCities(mapOf(it)) }
        updateState(category = Success(data), page = Page.DETAIL)
    }


    private fun saveCities(data: Map<String, CityWithRelationship>) {
        updateState(state.cities.map { it + data })
        saveCities(data) { updateState() }
    }

    private fun saveCities(
        data: Map<String, CityWithRelationship>,
        block: suspend CoroutineScope.(ResState<Any>) -> Unit
    ) = scope.launch { SaveCitiesUseCase(data.map { it.value }).collect { block(it) } }


    private fun deleteCities(data: Map<String, City>) {
        updateState(state.cities.map { it.minus(data.keys) })
        deleteCities(data) { updateState() }
    }

    private fun deleteCities(
        data: Map<String, City>,
        block: suspend CoroutineScope.(ResState<Any>) -> Unit
    ) = scope.launch { DeleteCitiesUseCase(data.map { it.value }).collect { block(it) } }


    private fun loadCities() = collectCities { updateState(it) }

    private fun collectCities(
        block: suspend CoroutineScope.(ResState<Map<String, CityWithRelationship>>) -> Unit
    ) {
        listJob?.cancel()
        listJob = scope.launch { GetCitiesUseCase().collect { block(it) } }
    }

    private fun openCity(id: String) {
        (state.city as? Success)?.data?.let { saveCities(mapOf(it)) }
        updateState(page = Page.DETAIL)
        loadCity(id)
    }

    private fun loadCity(id: String) = collectCity(id) { updateState(category = it) }

    private fun collectCity(
        id: String,
        block: suspend CoroutineScope.(ResState<Pair<String, CityWithRelationship>>) -> Unit
    ) {
        detailJob?.cancel()
        detailJob = scope.launch { GetCitiesUseCase(id).collect { block(it) } }
    }


    private fun updateState(
        cities: ResState<Map<String, CityWithRelationship>> = state.cities,
        category: ResState<Pair<String, CityWithRelationship>> = state.city,
        page: Page = state.page,
        snackbar: SnackbarData = state.snackbar,
    ) {
        _state.update { it.copy(city = category, cities = cities, page = page, snackbar = snackbar) }
    }
}
