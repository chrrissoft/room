package com.chrrissoft.room.countries.view.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chrrissoft.room.countries.db.objects.CountryWithRelationship

@Composable
fun CountriesList(
    state: List<CountryWithRelationship>,
    onSelect: (CountryWithRelationship) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier) {
        items(state) {

        }
    }
}
