package com.chrrissoft.room.cities.view.states

import com.chrrissoft.room.base.view.state.BaseState
import com.chrrissoft.room.cities.db.objects.CityWithRelationship
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.view.Page
import com.chrrissoft.room.ui.entities.SnackbarData

data class CitiesState(
    val detail: ResState<Pair<String, CityWithRelationship>> = ResState.None,
    val listing: ResState<Map<String, CityWithRelationship>> = ResState.Loading,
    override val snackbar: SnackbarData = SnackbarData(),
    val page: Page = Page.LIST,
) : BaseState()
