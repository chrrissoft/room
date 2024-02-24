package com.chrrissoft.room.carriers.view.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.chrrissoft.room.carriers.db.objects.CarrierWithRelationship
import com.chrrissoft.room.cities.db.objects.CityWithRelationship
import com.chrrissoft.room.cities.view.ui.CitiesListSheet
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.view.ResState
import com.chrrissoft.room.utils.PairUtils.mapSecond

@Composable
fun CarrierWithRelationship(
    state: ResState<Pair<String,CarrierWithRelationship>>,
    onStateChange: (Pair<String,CarrierWithRelationship>) -> Unit,
    cities: ResState<Map<String, CityWithRelationship>>,
    modifier: Modifier = Modifier,
) {
    var showCities by remember { mutableStateOf(value = false) }

    ResState(state = state) { success ->
        if (showCities) {
            CitiesListSheet(
                state = cities,
                onDelete = {  },
                selected = setOf(success.second.city.id),
                onSelect = { onStateChange(success.mapSecond { copy(city = it.second.city)}) },
                onDismissRequest = { showCities = false },
            )
        }


        Column(modifier) {
            Carrier(
                state = success.second.carrier,
                onStateChange = { change -> onStateChange(success.mapSecond { copy(carrier = change)}) }
            )
        }
    }
}
