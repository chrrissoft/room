package com.chrrissoft.room.cities.view.states

import com.chrrissoft.room.base.view.state.BaseState
import com.chrrissoft.room.cities.db.objects.CityWithRelationship
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.ui.entities.SnackbarData

data class CitiesState(
    val category: ResState<Pair<String, CityWithRelationship>> = ResState.None,
    val cities: ResState<Map<String, CityWithRelationship>> = ResState.Loading,
    override val snackbar: SnackbarData = SnackbarData(),
) : BaseState()
