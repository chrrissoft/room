package com.chrrissoft.room.shipments.view.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chrrissoft.room.shipments.db.objects.Shipping.ShippingState

@Composable
fun ShippingState(
    state: ShippingState,
    onStateChange: (ShippingState) -> Unit,
    modifier: Modifier = Modifier,
) {

}
