package com.chrrissoft.room.payments.view.states

import com.chrrissoft.room.base.view.state.BaseState
import com.chrrissoft.room.payments.db.objects.PaymentWithRelationship
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.ui.entities.SnackbarData

data class PaymentsState(
    val payment: ResState<Pair<String, PaymentWithRelationship>> = ResState.None,
    val payments: ResState<Map<String, PaymentWithRelationship>> = ResState.Loading,
    override val snackbar: SnackbarData = SnackbarData(),
) : BaseState()
