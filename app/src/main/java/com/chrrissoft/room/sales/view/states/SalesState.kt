package com.chrrissoft.room.sales.view.states

import com.chrrissoft.room.base.view.state.BaseState
import com.chrrissoft.room.sales.db.objects.SaleWithRelationship
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.view.Page
import com.chrrissoft.room.ui.entities.SnackbarData

data class SalesState(
    val sale: ResState<Pair<String, SaleWithRelationship>> = ResState.None,
    val sales: ResState<Map<String, SaleWithRelationship>> = ResState.Loading,
    override val snackbar: SnackbarData = SnackbarData(),
    val page: Page = Page.LIST
) : BaseState()
