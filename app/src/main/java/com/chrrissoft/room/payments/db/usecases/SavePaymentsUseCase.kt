package com.chrrissoft.room.payments.db.usecases

import com.chrrissoft.room.payments.db.objects.PaymentWithRelationship
import com.chrrissoft.room.payments.db.entities.PaymentDao
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.app.ResState.Success
import com.chrrissoft.room.utils.FlowUtils.ResFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SavePaymentsUseCase @Inject constructor(private val dao: PaymentDao) {
    operator fun invoke(data: List<PaymentWithRelationship>): Flow<ResState<Any>> {
        return ResFlow {
            dao.insert(data.map { it.payment })
            emit(Success(data = 0))
        }
    }

    operator fun invoke(vararg data: PaymentWithRelationship) : Flow<ResState<Any>> {
        return invoke(data.toList())
    }
}
