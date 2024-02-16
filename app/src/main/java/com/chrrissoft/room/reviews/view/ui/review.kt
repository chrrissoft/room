package com.chrrissoft.room.reviews.view.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chrrissoft.room.reviews.db.objects.Review
import com.chrrissoft.room.ui.components.RoomTextField

@Composable
fun Review(
    state: Review,
    onStateChange: (Review) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        RoomTextField(value = state.text, onValueChange = { onStateChange(state.copy(text = it)) })
    }
}
