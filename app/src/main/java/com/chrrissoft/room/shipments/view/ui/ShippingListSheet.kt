package com.chrrissoft.room.shipments.view.ui

import androidx.compose.runtime.Composable
import com.chrrissoft.room.shipments.db.objects.ShippingWithRelationship

@Composable
fun ShippingListSheet(
    state: List<ShippingWithRelationship>,
    selected: Set<String>,
    onSelect: (ShippingWithRelationship) -> Unit,
    onDismissRequest: () -> Unit
) {

}