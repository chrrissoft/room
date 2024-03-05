package com.chrrissoft.room.shipments.view.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shipments.db.objects.ShippingWithRelationship
import com.chrrissoft.room.ui.components.MyModalBottomSheet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShippingListSheet(
    state: ResState<Map<String, ShippingWithRelationship>>,
    selected: Set<String?>,
    onSelect: (Pair<String, ShippingWithRelationship>) -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    onDelete: ((Map<String, ShippingWithRelationship>) -> Unit)? = null,
) {
    MyModalBottomSheet(
        onDismissRequest = onDismissRequest,
        content = { ShipmentsList(state, onSelect, selected, onDelete = onDelete) },
        modifier = modifier
    )
}