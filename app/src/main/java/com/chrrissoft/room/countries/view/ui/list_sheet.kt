package com.chrrissoft.room.countries.view.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chrrissoft.room.countries.db.objects.CountryWithRelationship
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.ui.components.MyModalBottomSheet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountriesListSheet(
    state: ResState<Map<String, CountryWithRelationship>>,
    onSelect: (Pair<String, CountryWithRelationship>) -> Unit,
    selected: Set<String?>,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    onDelete: ((Map<String, CountryWithRelationship>) -> Unit)? = null,
) {
    MyModalBottomSheet(
        onDismissRequest = onDismissRequest,
        content = { CountriesList(state, onSelect, selected, onDelete = onDelete) },
        modifier = modifier
    )
}