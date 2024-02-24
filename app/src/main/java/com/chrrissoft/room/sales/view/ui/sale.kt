package com.chrrissoft.room.sales.view.ui

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.chrrissoft.room.payments.view.ui.Payment
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
            selected = listOf(state.payment),
            onSelect = { onStateChange(state.copy(payment = it)).let { showPayments = false } },
            onDismissRequest = { showPayments = false })
    }

    RoomTextField(
        value = state.description,
        onValueChange = { onStateChange(state.copy(description = it)) },
        modifier = modifier,
    )
    Spacer(modifier = Modifier.height(10.dp))
    SelectableRoomTextField(
        label = "Payment",
        value = state.payment.name,
        onClick = { showPayments = true },
    )
}
