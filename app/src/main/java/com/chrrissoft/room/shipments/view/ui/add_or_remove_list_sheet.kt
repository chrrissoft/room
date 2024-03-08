package com.chrrissoft.room.shipments.view.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chrrissoft.room.shipments.db.objects.ShippingWithRelationship
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.ui.components.MyModalBottomSheet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AndOrRemoveShippingListSheet(
    added: ResState<Map<String, ShippingWithRelationship>>,
    available: ResState<Map<String, ShippingWithRelationship>>,
    onRemove: (Map<String, ShippingWithRelationship>) -> Unit,
    onAdd: (Map<String, ShippingWithRelationship>) -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
) {
    MyModalBottomSheet(
        onDismissRequest = onDismissRequest,
        content = { AndOrRemoveShippingList(added, available, onRemove, onAdd) },
        modifier = modifier
    )
}
