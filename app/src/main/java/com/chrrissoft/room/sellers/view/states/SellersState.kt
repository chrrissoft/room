package com.chrrissoft.room.sellers.view.states

import com.chrrissoft.room.base.view.state.BaseState
import com.chrrissoft.room.sellers.db.objects.SellerWithRelationship
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.view.Page
import com.chrrissoft.room.ui.entities.SnackbarData

data class SellersState(
    val detail: ResState<Pair<String, SellerWithRelationship>> = ResState.None,
    val listing: ResState<Map<String, SellerWithRelationship>> = ResState.Loading,
    override val snackbar: SnackbarData = SnackbarData(),
    val page: Page = Page.LIST,
) : BaseState()
