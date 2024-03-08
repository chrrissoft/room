package com.chrrissoft.room.countries.view.screens

import androidx.compose.runtime.Composable
import com.chrrissoft.room.cities.view.states.CitiesState
import com.chrrissoft.room.countries.db.objects.Country
import com.chrrissoft.room.countries.db.objects.CountryNestedWithRelationship
import com.chrrissoft.room.countries.view.events.CountriesEvent
import com.chrrissoft.room.countries.view.events.CountriesEvent.OnChange
import com.chrrissoft.room.countries.view.events.CountriesEvent.OnChangePage
import com.chrrissoft.room.countries.view.events.CountriesEvent.OnCreate
import com.chrrissoft.room.countries.view.events.CountriesEvent.OnDelete
import com.chrrissoft.room.countries.view.events.CountriesEvent.OnOpen
import com.chrrissoft.room.countries.view.events.CountriesEvent.OnSave
import com.chrrissoft.room.countries.view.states.CountriesState
import com.chrrissoft.room.countries.view.ui.CountriesList
import com.chrrissoft.room.countries.view.ui.CountryWithRelationship
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.ui.components.CommonScreen
import com.chrrissoft.room.utils.ResStateUtils.getSuccess

@Composable
fun CountriesScreen(
    state: CountriesState,
    onEvent: (CountriesEvent) -> Unit,
    citiesState: CitiesState,
    onOpenDrawer: () -> Unit,
) {
    CommonScreen(
        page = state.page,
        title = "Countries",
        onChangePage = { onEvent(OnChangePage(it)) },
        onSave = { state.detail.getSuccess()?.let { onEvent(OnSave(it)) } },
        onCreate = { onEvent(OnCreate(it to CountryNestedWithRelationship(Country(it)))) },
        onNavigation = onOpenDrawer,
        details = {
            CountryWithRelationship(
                state = state.detail,
                onStateChange = { onEvent(OnChange(it)) },
                availableCities = ResState.None,
                addedCities = ResState.None,
                onRemoveCities = {},
                onAddCities = {},
            )
        },
        list = {
            CountriesList(
                state = state.listing,
                onDelete = { onEvent(OnDelete(it)) },
                selected = setOf(state.detail.getSuccess()?.first),
                onSelect = { onEvent(OnOpen(it)) },
            )
        },
    )
}
