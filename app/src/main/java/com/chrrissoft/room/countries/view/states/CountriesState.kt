package com.chrrissoft.room.countries.view.states

import com.chrrissoft.room.base.view.state.BaseState
import com.chrrissoft.room.countries.db.objects.CountryNestedWithRelationship
import com.chrrissoft.room.countries.db.objects.CountryWithRelationship
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.view.Page
import com.chrrissoft.room.ui.entities.SnackbarData

data class CountriesState(
    val detail: ResState<Pair<String, CountryNestedWithRelationship>> = ResState.None,
    val listing: ResState<Map<String, CountryWithRelationship>> = ResState.Loading,
    override val snackbar: SnackbarData = SnackbarData(),
    val page: Page = Page.LIST,
) : BaseState()
