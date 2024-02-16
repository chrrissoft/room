package com.chrrissoft.room.suppliers.view.states

import com.chrrissoft.room.base.view.state.BaseState
import com.chrrissoft.room.suppliers.db.objects.SupplierWithRelationship
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.ui.entities.SnackbarData

data class SuppliersState(
    val supplier: ResState<Pair<String, SupplierWithRelationship>> = ResState.None,
    val suppliers: ResState<Map<String, SupplierWithRelationship>> = ResState.Loading,
    override val snackbar: SnackbarData = SnackbarData(),
) : BaseState()
