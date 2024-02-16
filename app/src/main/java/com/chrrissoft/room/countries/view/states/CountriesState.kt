package com.chrrissoft.room.countries.view.states

import com.chrrissoft.room.base.view.state.BaseState
import com.chrrissoft.room.countries.db.objects.CountryWithRelationship
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.ui.entities.SnackbarData

data class CountriesState(
    val country: ResState<Pair<String, CountryWithRelationship>> = ResState.None,
    val countries: ResState<Map<String, CountryWithRelationship>> = ResState.Loading,
    override val snackbar: SnackbarData = SnackbarData(),
) : BaseState()
