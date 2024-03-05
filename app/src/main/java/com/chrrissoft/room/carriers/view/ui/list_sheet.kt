package com.chrrissoft.room.carriers.view.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chrrissoft.room.carriers.db.objects.CarrierWithRelationship
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.ui.components.MyModalBottomSheet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarrierListSheet(
    state: ResState<Map<String, CarrierWithRelationship>>,
    onSelect: (Pair<String, CarrierWithRelationship>) -> Unit,
    selected: Set<String?>,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    onDelete: ((Map<String, CarrierWithRelationship>) -> Unit)? = null,
) {
    MyModalBottomSheet(
        onDismissRequest = onDismissRequest,
        content = {
            CarriersList(state, selected, onSelect, onDelete = onDelete)
        },
        modifier = modifier
    )
}