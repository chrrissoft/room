package com.chrrissoft.room.reviews.view.states

import com.chrrissoft.room.base.view.state.BaseState
import com.chrrissoft.room.reviews.db.objects.ReviewWithRelationship
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.ui.entities.SnackbarData

data class ReviewsState(
    val review: ResState<Pair<String, ReviewWithRelationship>> = ResState.None,
    val reviews: ResState<Map<String, ReviewWithRelationship>> = ResState.Loading,
    override val snackbar: SnackbarData = SnackbarData(),
) : BaseState()
