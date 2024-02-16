package com.chrrissoft.room.payments.view.viewmodels

import com.chrrissoft.room.base.view.handler.BaseEventHandler
import com.chrrissoft.room.base.view.viewmodel.BaseViewModel
import com.chrrissoft.room.payments.db.objects.Payment
import com.chrrissoft.room.payments.db.objects.PaymentWithRelationship
import com.chrrissoft.room.payments.db.usecases.DeletePaymentsUseCase
import com.chrrissoft.room.payments.db.usecases.GetPaymentsUseCase
import com.chrrissoft.room.payments.db.usecases.SavePaymentsUseCase
import com.chrrissoft.room.payments.view.events.PaymentsEvent.OnChange
import com.chrrissoft.room.payments.view.events.PaymentsEvent.OnCreate
import com.chrrissoft.room.payments.view.events.PaymentsEvent.OnDelete
import com.chrrissoft.room.payments.view.events.PaymentsEvent.OnOpen
import com.chrrissoft.room.payments.view.events.PaymentsEvent.OnSave
import com.chrrissoft.room.payments.view.states.PaymentsState
import com.chrrissoft.room.payments.view.viewmodels.PaymentsViewModel.EventHandler
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.app.ResState.Success
import com.chrrissoft.room.ui.entities.SnackbarData
import com.chrrissoft.room.utils.ResStateUtils.map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentsViewModel @Inject constructor(
    private val GetPaymentsUseCase: GetPaymentsUseCase,
    private val SavePaymentsUseCase: SavePaymentsUseCase,
    private val DeletePaymentsUseCase: DeletePaymentsUseCase,
) : BaseViewModel<EventHandler, PaymentsState>() {
    override val eventHandler = EventHandler()
    override val _state = MutableStateFlow(PaymentsState())
    override val stateFlow = _state.asStateFlow()

    init {
        loadPayments()
    }

    inner class EventHandler : BaseEventHandler() {
        fun onEvent(event: OnOpen) = loadPayment(event.data)

        fun onEvent(event: OnSave) = savePayments(mapOf(event.data))

        fun onEvent(event: OnCreate) = create(event.data)

        fun onEvent(event: OnChange) = updateState(payment = Success(event.data))

        fun onEvent(event: OnDelete) = deletePayments(event.data.mapValues { it.value.payment })
    }


    private fun create(data: Pair<String, PaymentWithRelationship>) {
        (state.payment as? Success)?.data?.let { savePayments(mapOf(it)) }
        updateState(payment = Success(data))
    }


    private fun savePayments(data: Map<String, PaymentWithRelationship>) {
        updateState(state.payments.map { it + data })
        savePayments(data) { updateState() }
    }

    private fun savePayments(
        data: Map<String, PaymentWithRelationship>,
        block: suspend CoroutineScope.(ResState<Any>) -> Unit
    ) = scope.launch { SavePaymentsUseCase(data.map { it.value }).collect { block(it) } }


    private fun deletePayments(data: Map<String, Payment>) {
        updateState(state.payments.map { it.minus(data.keys) })
        deletePayments(data) { updateState() }
    }

    private fun deletePayments(
        data: Map<String, Payment>,
        block: suspend CoroutineScope.(ResState<Any>) -> Unit
    ) = scope.launch { DeletePaymentsUseCase(data.map { it.value }).collect { block(it) } }


    private fun loadPayments() = collectPayments { updateState(it) }

    private fun collectPayments(
        block: suspend CoroutineScope.(ResState<Map<String, PaymentWithRelationship>>) -> Unit
    ) = scope.launch { GetPaymentsUseCase().collect { block(it) } }


    private fun loadPayment(id: String) = collectPayment(id) { updateState(payment = it) }

    private fun collectPayment(
        id: String,
        block: suspend CoroutineScope.(ResState<Pair<String, PaymentWithRelationship>>) -> Unit
    ) = scope.launch { GetPaymentsUseCase(id).collect { block(it) } }


    private fun updateState(
        payments: ResState<Map<String, PaymentWithRelationship>> = state.payments,
        payment: ResState<Pair<String, PaymentWithRelationship>> = state.payment,
        snackbar: SnackbarData = state.snackbar,
    ) {
        _state.update { it.copy(payment = payment, payments = payments, snackbar = snackbar) }
    }
}
