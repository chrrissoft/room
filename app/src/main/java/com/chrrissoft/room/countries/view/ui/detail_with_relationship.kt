package com.chrrissoft.room.countries.view.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.chrrissoft.room.cities.db.objects.CityWithRelationship
import com.chrrissoft.room.cities.view.ui.AndOrRemoveCityListSheet
import com.chrrissoft.room.countries.db.objects.CountryNestedWithRelationship
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.app.ResState.Success
import com.chrrissoft.room.shared.view.ResState
import com.chrrissoft.room.ui.components.RoomDivider
import com.chrrissoft.room.ui.components.SelectableRoomTextField
import com.chrrissoft.room.utils.PairUtils.mapSecond
import com.chrrissoft.room.utils.ResStateUtils.map

@Composable
fun CountryWithRelationship(
    state: ResState<Pair<String, CountryNestedWithRelationship>>,
    onStateChange: (Pair<String, CountryNestedWithRelationship>) -> Unit,
    cities: ResState<Map<String, CityWithRelationship>>,
    onRemoveCities: ((Map<String, CityWithRelationship>) -> Unit)?,
    onAddCities: (Map<String, CityWithRelationship>) -> Unit,
    modifier: Modifier = Modifier,
) {

    ResState(state = state) { pair ->
        Column(modifier) {
            val data by remember(pair) { mutableStateOf(pair.second) }

            var showCities by remember { mutableStateOf(value = false) }

            if (showCities) {
                var availableCities by remember(cities) { mutableStateOf(cities) }
                LaunchedEffect(data.cities) {
                    cities.map { map -> map.filterNot { data.cities.contains(it.value) } }
                        .let { availableCities = it }
                }

                AndOrRemoveCityListSheet(
                    added = Success(data.cities.associateBy { it.city.id }),
                    available = availableCities,
                    onRemove = onRemoveCities,
                    onAdd = onAddCities,
                    onDismissRequest = { showCities = false }
                )
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
                value = data.cities.joinToString(limit = 3) { it.city.name },
            )
        }
    }
}
