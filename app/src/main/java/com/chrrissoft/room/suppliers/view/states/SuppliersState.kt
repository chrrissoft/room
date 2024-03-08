package com.chrrissoft.room.suppliers.view.states

import com.chrrissoft.room.base.view.state.BaseState
import com.chrrissoft.room.suppliers.db.objects.SupplierWithRelationship
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.view.Page
import com.chrrissoft.room.suppliers.db.objects.SupplierWithNestedRelationship
import com.chrrissoft.room.ui.entities.SnackbarData

data class SuppliersState(
    val detail: ResState<Pair<String, SupplierWithNestedRelationship>> = ResState.None,
    val listing: ResState<Map<String, SupplierWithRelationship>> = ResState.Loading,
    override val snackbar: SnackbarData = SnackbarData(),
    val page: Page = Page.LIST,
) : BaseState()
