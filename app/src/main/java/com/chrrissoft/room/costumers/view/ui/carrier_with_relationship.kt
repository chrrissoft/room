package com.chrrissoft.room.costumers.view.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.chrrissoft.room.costumers.db.objects.CostumerWithRelationship
import com.chrrissoft.room.cities.db.objects.CityWithRelationship
import com.chrrissoft.room.cities.view.ui.CitiesListSheet
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.view.ResState
import com.chrrissoft.room.ui.components.SelectableRoomTextField
import com.chrrissoft.room.utils.PairUtils.mapSecond

@Composable
fun CostumerWithRelationship(
    state: ResState<Pair<String, CostumerWithRelationship>>,
    onStateChange: (Pair<String, CostumerWithRelationship>) -> Unit,
    cities: ResState<Map<String, CityWithRelationship>>,
    modifier: Modifier = Modifier,
) {
    ResState(state = state) {
        val data = remember(it.second) { it.second }

        var showCities by remember { mutableStateOf(value = false) }

        if (showCities) {
            CitiesListSheet(
                state = cities,
                onDelete = { },
                selected = setOf(data.city.id),
                onSelect = { change -> onStateChange(it.mapSecond { copy(city = change.second.city) }) },
                onDismissRequest = { showCities = false },
            )
        }


        Column(modifier) {
            Costumer(
                state = data.costumer,
                onStateChange = { change -> onStateChange(it.mapSecond { copy(costumer = change) }) }
            )
            SelectableRoomTextField(
                value = data.city.name,
                label = "City",
                onClick = { showCities = true },
            )
        }
    }
}
