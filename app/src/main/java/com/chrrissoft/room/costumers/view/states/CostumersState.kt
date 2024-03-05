package com.chrrissoft.room.costumers.view.states

import com.chrrissoft.room.base.view.state.BaseState
import com.chrrissoft.room.costumers.db.objects.CostumerWithRelationship
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.view.Page
import com.chrrissoft.room.ui.entities.SnackbarData

data class CostumersState(
    val detail: ResState<Pair<String, CostumerWithRelationship>> = ResState.None,
    val listing: ResState<Map<String, CostumerWithRelationship>> = ResState.Loading,
    override val snackbar: SnackbarData = SnackbarData(),
    val page: Page = Page.LIST,
) : BaseState()
