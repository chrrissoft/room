package com.chrrissoft.room.inventories.view.states

import com.chrrissoft.room.base.view.state.BaseState
import com.chrrissoft.room.inventories.db.objects.InventoryWithRelationship
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.ui.entities.SnackbarData

data class InventoriesState(
    val inventory: ResState<Pair<String, InventoryWithRelationship>> = ResState.None,
    val inventories: ResState<Map<String, InventoryWithRelationship>> = ResState.Loading,
    override val snackbar: SnackbarData = SnackbarData(),
) : BaseState()
