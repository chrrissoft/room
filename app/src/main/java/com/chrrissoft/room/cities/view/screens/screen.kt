package com.chrrissoft.room.cities.view.screens

import androidx.compose.runtime.Composable
import com.chrrissoft.room.cities.db.objects.City
import com.chrrissoft.room.cities.db.objects.CityWithRelationship
import com.chrrissoft.room.cities.view.events.CitiesEvent
import com.chrrissoft.room.cities.view.states.CitiesState
import com.chrrissoft.room.cities.view.ui.CitiesList
import com.chrrissoft.room.cities.view.ui.CityWithRelationship
import com.chrrissoft.room.countries.view.events.CountriesEvent
import com.chrrissoft.room.countries.view.states.CountriesState
import com.chrrissoft.room.ui.components.AlarmManagerSnackbar
import com.chrrissoft.room.ui.components.CommonScreen
import com.chrrissoft.room.utils.ResStateUtils.getSuccess

@Composable
fun CitiesScreen(
    state: CitiesState,
    onEvent: (CitiesEvent) -> Unit,
    countriesState: CountriesState,
    onCountriesEvent: (CountriesEvent) -> Unit,
    onOpenDrawer: () -> Unit,
) {
    CommonScreen(
        page = state.page,
        title = "Cities",
        onChangePage = { onEvent(CitiesEvent.OnChangePage(it)) },
        onSave = { state.city.getSuccess()?.let { onEvent(CitiesEvent.OnSave(it)) } },
        onCreate = { onEvent(CitiesEvent.OnCreate(it to CityWithRelationship(City(it)))) },
        onNavigation = onOpenDrawer,
        details = {
            CityWithRelationship(
                state = state.city,
                countries = countriesState.countries,
                onStateChange = { onEvent(CitiesEvent.OnChange(it)) },
            )
        },
        list = {
            CitiesList(
                state = state.cities,
                onDelete = { onEvent(CitiesEvent.OnDelete(it)) },
                selected = setOf(),
                onSelect = { onEvent(CitiesEvent.OnOpen(it.first)) },
            )
        },
        snackbarHost = { AlarmManagerSnackbar(state = state.snackbar) }
    )
}
