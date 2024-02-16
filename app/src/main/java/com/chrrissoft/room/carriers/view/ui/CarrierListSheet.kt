package com.chrrissoft.room.carriers.view.ui

import androidx.compose.runtime.Composable
import com.chrrissoft.room.carriers.db.objects.CarrierWithRelationship

@Composable
fun CarrierListSheet(
    state: List<CarrierWithRelationship>,
    onSelect: (CarrierWithRelationship) -> Unit,
    selected: Set<String>,
    onDismissRequest: () -> Unit
) {

}