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
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.view.ResState
import com.chrrissoft.room.ui.components.RoomDivider
import com.chrrissoft.room.ui.components.SelectableRoomTextField
import com.chrrissoft.room.utils.PairUtils.mapSecond

@Composable
fun CityWithRelationship(
    state: ResState<Pair<String, CityWithRelationship>>,
    onStateChange: (Pair<String, CityWithRelationship>) -> Unit,
    countries: ResState<Map<String, CountryWithRelationship>>,
    modifier: Modifier = Modifier,
) {
    var showCountries by remember { mutableStateOf(value = false) }

    ResState(state = state) { pair ->
        if (showCountries) {
            CountriesListSheet(
                state = countries,
                onSelect = {
                    val city = pair.second.city.copy(countryId = it.first)
                    pair.mapSecond { copy(city = city, country = it.second.country) }
                        .let(onStateChange)
                },
                selected = setOf(pair.second.country.id),
                onDismissRequest = { showCountries = false },
            )
        }


        Column(modifier) {
            City(
                state = pair.second.city,
                onStateChange = { onStateChange(pair.mapSecond { copy(city = it) }) }
            )
            RoomDivider()
            SelectableRoomTextField(
                label = "Country",
                selected = showCountries,
                value = pair.second.country.name,
                onClick = { showCountries = true },
            )
        }
    }
}
