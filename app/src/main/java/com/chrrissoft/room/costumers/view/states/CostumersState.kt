package com.chrrissoft.room.costumers.view.states

import com.chrrissoft.room.base.view.state.BaseState
import com.chrrissoft.room.costumers.db.objects.CostumerWithRelationship
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.ui.entities.SnackbarData

data class CostumersState(
    val costumer: ResState<Pair<String, CostumerWithRelationship>> = ResState.None,
    val costumers: ResState<Map<String, CostumerWithRelationship>> = ResState.Loading,
    override val snackbar: SnackbarData = SnackbarData(),
) : BaseState()
