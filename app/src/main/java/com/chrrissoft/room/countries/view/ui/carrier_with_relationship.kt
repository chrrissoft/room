package com.chrrissoft.room.countries.view.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.chrrissoft.room.cities.db.objects.CityWithRelationship
import com.chrrissoft.room.cities.view.ui.CitiesListSheet
import com.chrrissoft.room.countries.db.objects.CountryWithRelationship
import com.chrrissoft.room.ui.components.SelectableRoomTextField

@Composable
fun CountryWithRelationship(
    state: CountryWithRelationship,
    onStateChange: (CountryWithRelationship) -> Unit,
    cities: List<CityWithRelationship>,
    modifier: Modifier = Modifier,
) {
    var showCities by remember { mutableStateOf(value = false) }

    if (showCities) {
        val selected = remember(state.cities) { state.cities.mapTo(mutableSetOf()) { it.id } }
        CitiesListSheet(
            state = cities,
            onSelect = { city ->
                (if (state.cities.contains(city.city)) state.cities.minus(city.city)
                else state.cities.plus(city.city)).let { onStateChange(state.copy(cities = it)) }
            },
            selected = selected,
            onDismissRequest = { showCities = false })
    }


    Column(modifier) {
        Country(
            state = state.country,
            onStateChange = { onStateChange(state.copy(country = it)) }
        )
        SelectableRoomTextField(value = state.cities.joinToString { ", " }, onClick = {})
    }
}
