package com.chrrissoft.room.payments.view.ui

import androidx.compose.runtime.Composable
import com.chrrissoft.room.payments.db.objects.PaymentWithRelationship

@Composable
fun PaymentListSheet(
    state: List<PaymentWithRelationship>,
    selected: Set<String>,
    onSelect: (PaymentWithRelationship) -> Unit,
    onDismissRequest: () -> Unit
) {
}