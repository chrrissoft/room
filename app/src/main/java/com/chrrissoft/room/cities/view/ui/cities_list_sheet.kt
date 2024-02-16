package com.chrrissoft.room.cities.view.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chrrissoft.room.cities.db.objects.City
import com.chrrissoft.room.cities.db.objects.CityWithRelationship
import com.chrrissoft.room.ui.components.MyModalBottomSheet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CitiesListSheet(
    state: List<CityWithRelationship>,
    onSelect: (CityWithRelationship) -> Unit,
    selected: Set<String>,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
) {
    MyModalBottomSheet(
        onDismissRequest = onDismissRequest,
        content = { CitiesList(state = state, onSelect = onSelect) },
        modifier = modifier
    )
}