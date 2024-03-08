package com.chrrissoft.room.orders.db.usecases

import com.chrrissoft.room.orders.db.objects.OrderWithRelationship
import com.chrrissoft.room.orders.db.entities.OrderDao
import com.chrrissoft.room.orders.db.objects.OrderWithNestedRelationship
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.app.ResState.Success
import com.chrrissoft.room.utils.FlowUtils.ResFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetOrdersUseCase @Inject constructor(private val dao: OrderDao) {
    operator fun invoke(): Flow<ResState<Map<String, OrderWithRelationship>>> {
        return ResFlow {
            dao.get().collect { data -> emit(Success(data.associateBy { it.order.id })) }
        }
    }

    operator fun invoke(id: String): Flow<ResState<Pair<String, OrderWithNestedRelationship>>> {
        return ResFlow { dao.get(id).collect { emit(Success(it.order.id to it)) } }
    }
}
