package com.chrrissoft.room.sales.view.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.chrrissoft.room.payments.view.ui.PaymentListSheet
import com.chrrissoft.room.sales.db.objects.Sale
import com.chrrissoft.room.ui.components.RoomTextField
import com.chrrissoft.room.ui.components.SelectableRoomTextField

@Composable
fun Sale(
    state: Sale,
    onStateChange: (Sale) -> Unit,
    modifier: Modifier = Modifier,
) {
    var showPayments by remember { mutableStateOf(value = false) }

    if (showPayments) {
        PaymentListSheet(
            selected = state.payment,
            onSelect = { onStateChange(state.copy(payment = it)).let { showPayments = false } },
            onDismissRequest = { showPayments = false })
    }

    Column(modifier) {
        RoomTextField(
            label = "Name",
            value = state.name,
            onValueChange = { onStateChange(state.copy(name = it)) },
        )
        RoomTextField(
            label = "Description",
            value = state.description,
            onValueChange = { onStateChange(state.copy(description = it)) },
        )
        SelectableRoomTextField(
            label = "Payment",
            value = state.payment.name,
            selected = showPayments,
            onClick = { showPayments = true },
        )
    }
}
