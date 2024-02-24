package com.chrrissoft.room.payments.view.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Payment(
    state: Payment,
    onStateChange: (Payment) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column() {
        
    }
}
enum class Payment {
    CASH
    ;

    companion object {
        val payments: List<Payment> = emptyList()
    }

}