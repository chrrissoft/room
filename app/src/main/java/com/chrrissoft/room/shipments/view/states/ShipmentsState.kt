package com.chrrissoft.room.shipments.view.states

import com.chrrissoft.room.base.view.state.BaseState
import com.chrrissoft.room.shipments.db.objects.ShippingWithRelationship
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.view.Page
import com.chrrissoft.room.shipments.db.objects.ShippingWithNestedRelationship
import com.chrrissoft.room.ui.entities.SnackbarData

data class ShipmentsState(
    val detail: ResState<Pair<String, ShippingWithNestedRelationship>> = ResState.None,
    val listing: ResState<Map<String, ShippingWithRelationship>> = ResState.Loading,
    override val snackbar: SnackbarData = SnackbarData(),
    val page: Page = Page.LIST,
) : BaseState()
