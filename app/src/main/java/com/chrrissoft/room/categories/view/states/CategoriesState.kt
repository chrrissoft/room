package com.chrrissoft.room.categories.view.states

import com.chrrissoft.room.base.view.state.BaseState
import com.chrrissoft.room.categories.db.objects.CategoryWithNestedRelationship
import com.chrrissoft.room.categories.db.objects.CategoryWithRelationship
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.view.Page
import com.chrrissoft.room.ui.entities.SnackbarData

data class CategoriesState(
    val detail: ResState<Pair<String, CategoryWithNestedRelationship>> = ResState.None,
    val listing: ResState<Map<String, CategoryWithRelationship>> = ResState.Loading,
    override val snackbar: SnackbarData = SnackbarData(),
    val page: Page = Page.LIST
) : BaseState()
