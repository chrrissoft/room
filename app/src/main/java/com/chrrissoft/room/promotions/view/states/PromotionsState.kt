package com.chrrissoft.room.promotions.view.states

import com.chrrissoft.room.base.view.state.BaseState
import com.chrrissoft.room.promotions.db.objects.PromotionWithRelationship
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.view.Page
import com.chrrissoft.room.ui.entities.SnackbarData

data class PromotionsState(
    val promotion: ResState<Pair<String, PromotionWithRelationship>> = ResState.None,
    val promotions: ResState<Map<String, PromotionWithRelationship>> = ResState.Loading,
    override val snackbar: SnackbarData = SnackbarData(),
    val page: Page = Page.LIST
) : BaseState()
