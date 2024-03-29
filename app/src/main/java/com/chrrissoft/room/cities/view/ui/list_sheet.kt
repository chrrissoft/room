package com.chrrissoft.room.cities.view.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chrrissoft.room.cities.db.objects.CityWithRelationship
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.ui.components.MyModalBottomSheet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CitiesListSheet(
    state: ResState<Map<String, CityWithRelationship>>,
    onSelect: (Pair<String, CityWithRelationship>) -> Unit,
    selected: Set<String?>,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    onDelete: ((Map<String, CityWithRelationship>) -> Unit)? = null,
) {
    MyModalBottomSheet(
        onDismissRequest = onDismissRequest,
        content = { CitiesList(state, onSelect, selected, onDelete = onDelete) },
        modifier = modifier
    )
}
