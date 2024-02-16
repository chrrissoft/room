package com.chrrissoft.room.payments.db.usecases

import com.chrrissoft.room.payments.db.objects.PaymentWithRelationship
import com.chrrissoft.room.payments.db.entities.PaymentDao
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.app.ResState.Success
import com.chrrissoft.room.utils.FlowUtils.ResFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPaymentsUseCase @Inject constructor(private val dao: PaymentDao) {
    operator fun invoke(): Flow<ResState<Map<String, PaymentWithRelationship>>> {
        return ResFlow {
            dao.get().collect { data -> emit(Success(data.associateBy { it.payment.id })) }
        }
    }

    operator fun invoke(id: String): Flow<ResState<Pair<String, PaymentWithRelationship>>> {
        return ResFlow { dao.get(id).collect { emit(Success(it.payment.id to it)) } }
    }
}
