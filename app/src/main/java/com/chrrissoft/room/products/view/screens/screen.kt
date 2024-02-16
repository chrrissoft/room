package com.chrrissoft.room.products.view.screens

import androidx.compose.runtime.Composable
import com.chrrissoft.room.products.view.events.ProductsEvent
import com.chrrissoft.room.products.view.states.ProductsState

@Composable
fun ProductsScreen(
    state: ProductsState,
    onEvent: (ProductsEvent) -> Unit,
    onOpenDrawer: () -> Unit,
) {

}
