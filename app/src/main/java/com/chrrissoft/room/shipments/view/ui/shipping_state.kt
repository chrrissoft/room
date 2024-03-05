package com.chrrissoft.room.shipments.view.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.chrrissoft.room.shipments.db.objects.Shipping.ShippingState
import com.chrrissoft.room.ui.components.MyModalBottomSheet
import com.chrrissoft.room.ui.components.SelectableRoomTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShippingState(
    state: ShippingState,
    onStateChange: (ShippingState) -> Unit,
    modifier: Modifier = Modifier,
) {
    var showList by rememberSaveable { mutableStateOf(false) }

    if (showList) {
        MyModalBottomSheet(
            onDismissRequest = { showList = false },
            content = {
                LazyColumn {
                    items(ShippingState.stateList) {
                        SelectableRoomTextField(
                            value = it.name,
                            selected = it == state,
                            onClick = { onStateChange(it).let { showList = false } },
                        )
                    }
                }
            }
        )
    }

    SelectableRoomTextField(
        label = "State",
        value = state.name,
        selected = showList,
        onClick = { showList = true },
        modifier = modifier
    )
}
