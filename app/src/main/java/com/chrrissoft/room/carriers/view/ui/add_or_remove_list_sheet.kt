package com.chrrissoft.room.carriers.view.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chrrissoft.room.carriers.db.objects.CarrierWithRelationship
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.ui.components.MyModalBottomSheet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AndOrRemoveCarrierListSheet(
    added: ResState<Map<String, CarrierWithRelationship>>,
    available: ResState<Map<String, CarrierWithRelationship>>,
    onRemove: ((Map<String, CarrierWithRelationship>) -> Unit)?,
    onAdd: (Map<String, CarrierWithRelationship>) -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
) {
    MyModalBottomSheet(
        onDismissRequest = onDismissRequest,
        content = { AndOrRemoveCarrierList(added, available, onRemove, onAdd) },
        modifier = modifier
    )
}
