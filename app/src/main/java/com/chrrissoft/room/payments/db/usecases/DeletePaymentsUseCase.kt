package com.chrrissoft.room.payments.db.usecases

import com.chrrissoft.room.payments.db.objects.Payment
import com.chrrissoft.room.payments.db.entities.PaymentDao
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.utils.FlowUtils.ResFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeletePaymentsUseCase @Inject constructor(private val dao: PaymentDao) {
    operator fun invoke(data: List<Payment>): Flow<ResState<Any>> {
        return ResFlow { dao.delete(data) }
    }

    operator fun invoke(vararg data: Payment) : Flow<ResState<Any>> {
        return invoke(data.toList())
    }
}
