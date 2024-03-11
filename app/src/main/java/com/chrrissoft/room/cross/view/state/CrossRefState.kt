package com.chrrissoft.room.cross.view.state

import com.chrrissoft.room.base.view.state.BaseState
import com.chrrissoft.room.ui.entities.SnackbarData

data class CrossRefState(override val snackbar: SnackbarData = SnackbarData()) : BaseState()
