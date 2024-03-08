package com.chrrissoft.room.promotions.view.states

import com.chrrissoft.room.base.view.state.BaseState
import com.chrrissoft.room.promotions.db.objects.PromotionNestedWithRelationship
import com.chrrissoft.room.promotions.db.objects.PromotionWithRelationship
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.view.Page
import com.chrrissoft.room.ui.entities.SnackbarData

data class PromotionsState(
    val detail: ResState<Pair<String, PromotionNestedWithRelationship>> = ResState.None,
    val listing: ResState<Map<String, PromotionWithRelationship>> = ResState.Loading,
    override val snackbar: SnackbarData = SnackbarData(),
    val page: Page = Page.LIST,
) : BaseState()
