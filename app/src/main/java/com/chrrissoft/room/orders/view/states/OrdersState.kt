package com.chrrissoft.room.orders.view.states

import com.chrrissoft.room.base.view.state.BaseState
import com.chrrissoft.room.orders.db.objects.OrderWithNestedRelationship
import com.chrrissoft.room.orders.db.objects.OrderWithRelationship
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.view.Page
import com.chrrissoft.room.ui.entities.SnackbarData

data class OrdersState(
    val detail: ResState<Pair<String, OrderWithNestedRelationship>> = ResState.None,
    val listing: ResState<Map<String, OrderWithRelationship>> = ResState.Loading,
    override val snackbar: SnackbarData = SnackbarData(),
    val page: Page = Page.LIST,
) : BaseState()
