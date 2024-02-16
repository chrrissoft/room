package com.chrrissoft.room.orders.view.states

import com.chrrissoft.room.base.view.state.BaseState
import com.chrrissoft.room.orders.db.objects.OrderWithRelationship
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.ui.entities.SnackbarData

data class OrdersState(
    val order: ResState<Pair<String, OrderWithRelationship>> = ResState.None,
    val orders: ResState<Map<String, OrderWithRelationship>> = ResState.Loading,
    override val snackbar: SnackbarData = SnackbarData(),
) : BaseState()
