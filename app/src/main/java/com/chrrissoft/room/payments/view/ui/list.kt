package com.chrrissoft.room.payments.view.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chrrissoft.room.ui.components.SelectableRoomTextField

@Composable
fun PaymentList(
    state: Payment,
    onSelect: (Payment) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier) {
        items(Payment.payments) {
            SelectableRoomTextField(
                value = it.name,
                selected = state == it,
                onClick = {
                    onSelect(it)
                }
            )
        }
    }
}