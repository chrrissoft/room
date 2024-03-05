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
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.view.ResState
import com.chrrissoft.room.ui.components.RoomDivider
import com.chrrissoft.room.ui.components.SelectableRoomTextField
import com.chrrissoft.room.utils.PairUtils.mapSecond

@Composable
fun CountryWithRelationship(
    state: ResState<Pair<String, CountryWithRelationship>>,
    onStateChange: (Pair<String, CountryWithRelationship>) -> Unit,
    cities: ResState<Map<String, CityWithRelationship>>,
    modifier: Modifier = Modifier,
) {
    var showCities by remember { mutableStateOf(value = false) }

    ResState(state = state) { pair ->
        Column(modifier) {
            val data = remember(pair) { pair.second }

            if (showCities) {
                val selected = remember(data) { data.cities.mapTo(mutableSetOf()) { it.id } }
                CitiesListSheet(
                    state = cities,
                    onDelete = {  },
                    onSelect = { city ->
                        (if (data.cities.contains(city.second.city)) data.cities.minus(city.second.city)
                        else data.cities.plus(city.second.city))
                            .let { onStateChange(pair.mapSecond { copy(cities = it) }) }
                    },
                    selected = selected,
                    onDismissRequest = { showCities = false })
            }

            Country(
                state = data.country,
                onStateChange = { onStateChange(pair.mapSecond { copy(country = it) }) }
            )
            RoomDivider()
            SelectableRoomTextField(
                label = "Cities",
                selected = showCities,
                onClick = { showCities = true },
                value = data.cities.joinToString(limit = 3) { it.name },
            )
        }
    }
}
