package com.chrrissoft.room.cities.view.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.chrrissoft.room.cities.db.objects.CityWithRelationship
import com.chrrissoft.room.countries.db.objects.CountryWithRelationship
import com.chrrissoft.room.countries.view.ui.CountriesListSheet
import com.chrrissoft.room.ui.components.SelectableRoomTextField

@Composable
fun CityWithRelationship(
    state: CityWithRelationship,
    onStateChange: (CityWithRelationship) -> Unit,
    cities: List<CountryWithRelationship>,
    modifier: Modifier = Modifier,
) {
    var showCities by remember { mutableStateOf(value = false) }

    if (showCities) {
        CountriesListSheet(
            state = cities,
            onSelect = { onStateChange(state.copy(country = it.country)) },
            onDismissRequest = { showCities = false },
        )
    }


    Column(modifier) {
        City(
            state = state.city,
            onStateChange = { onStateChange(state.copy(city = it)) }
        )
        SelectableRoomTextField(
            value = state.city.name,
            label = "City",
            onClick = { showCities = true },
        )
    }
}
