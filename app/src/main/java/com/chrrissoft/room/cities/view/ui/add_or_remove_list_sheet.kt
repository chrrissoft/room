package com.chrrissoft.room.cities.view.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chrrissoft.room.cities.db.objects.CityWithRelationship
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.ui.components.MyModalBottomSheet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AndOrRemoveCityListSheet(
    added: ResState<Map<String, CityWithRelationship>>,
    available: ResState<Map<String, CityWithRelationship>>,
    onRemove: (Map<String, CityWithRelationship>) -> Unit,
    onAdd: (Map<String, CityWithRelationship>) -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
) {
    MyModalBottomSheet(
        onDismissRequest = onDismissRequest,
        content = { AndOrRemoveCityList(added, available, onRemove, onAdd) },
        modifier = modifier
    )
}
