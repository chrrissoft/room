package com.chrrissoft.room.categories.view.states

import com.chrrissoft.room.base.view.state.BaseState
import com.chrrissoft.room.categories.db.objects.CategoryWithRelationship
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.ui.entities.SnackbarData

data class CategoriesState(
    val category: ResState<Pair<String, CategoryWithRelationship>> = ResState.None,
    val categories: ResState<Map<String, CategoryWithRelationship>> = ResState.Loading,
    override val snackbar: SnackbarData = SnackbarData(),
) : BaseState()
