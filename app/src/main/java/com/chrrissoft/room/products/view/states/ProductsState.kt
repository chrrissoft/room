package com.chrrissoft.room.products.view.states

import com.chrrissoft.room.base.view.state.BaseState
import com.chrrissoft.room.products.db.objects.ProductWithRelationship
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.view.Page
import com.chrrissoft.room.ui.entities.SnackbarData

data class ProductsState(
    val detail: ResState<Pair<String, ProductWithRelationship>> = ResState.None,
    val listing: ResState<Map<String, ProductWithRelationship>> = ResState.Loading,
    override val snackbar: SnackbarData = SnackbarData(),
    val page: Page = Page.LIST,
) : BaseState()
