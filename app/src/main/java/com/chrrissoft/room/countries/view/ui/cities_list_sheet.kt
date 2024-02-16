package com.chrrissoft.room.countries.view.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chrrissoft.room.countries.db.objects.CountryWithRelationship
import com.chrrissoft.room.ui.components.MyModalBottomSheet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountriesListSheet(
    state: List<CountryWithRelationship>,
    onSelect: (CountryWithRelationship) -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
) {
    MyModalBottomSheet(
        onDismissRequest = onDismissRequest,
        content = { CountriesList(state = state, onSelect = onSelect) },
        modifier = modifier
    )
}