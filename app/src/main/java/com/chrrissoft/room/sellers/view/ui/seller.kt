package com.chrrissoft.room.sellers.view.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chrrissoft.room.sellers.db.objects.Seller
import com.chrrissoft.room.shared.view.PersonName

@Composable
fun Seller(
    state: Seller,
    onStateChange: (Seller) -> Unit,
    modifier: Modifier = Modifier,
) {
    PersonName(
        state = state.name,
        onStateChange = { onStateChange(state.copy(name = it)) },
        modifier = modifier
    )
}
