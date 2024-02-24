package com.chrrissoft.room.carriers.view.states

import com.chrrissoft.room.base.view.state.BaseState
import com.chrrissoft.room.carriers.db.objects.CarrierWithRelationship
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.view.Page
import com.chrrissoft.room.ui.entities.SnackbarData

data class CarriersState(
    val carrier: ResState<Pair<String, CarrierWithRelationship>> = ResState.None,
    val carriers: ResState<Map<String, CarrierWithRelationship>> = ResState.Loading,
    override val snackbar: SnackbarData = SnackbarData(),
    val page: Page = Page.LIST,
) : BaseState()
