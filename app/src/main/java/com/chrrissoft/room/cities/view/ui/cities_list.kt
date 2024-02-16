package com.chrrissoft.room.cities.view.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chrrissoft.room.cities.db.objects.City
import com.chrrissoft.room.cities.db.objects.CityWithRelationship

@Composable
fun CitiesList(
    state: List<CityWithRelationship>,
    onSelect: (CityWithRelationship) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier) {
        items(state) {

        }
    }
}
