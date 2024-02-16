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
import com.chrrissoft.room.ui.components.SelectableRoomTextField

@Composable
fun CostumerWithRelationship(
    state: CostumerWithRelationship,
    onStateChange: (CostumerWithRelationship) -> Unit,
    cities: List<CityWithRelationship>,
    modifier: Modifier = Modifier,
) {
    var showCities by remember { mutableStateOf(value = false) }

    if (showCities) {
        CitiesListSheet(
            state = cities,
            selected = setOf(state.city.id),
            onSelect = { onStateChange(state.copy(city = it.city)) },
            onDismissRequest = { showCities = false },
        )
    }


    Column(modifier) {
        Costumer(
            state = state.costumer,
            onStateChange = { onStateChange(state.copy(costumer = it)) }
        )
        SelectableRoomTextField(
            value = state.city.name,
            label = "City",
            onClick = { showCities = true },
        )
    }
}
