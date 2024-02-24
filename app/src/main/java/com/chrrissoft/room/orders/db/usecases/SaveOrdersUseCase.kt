package com.chrrissoft.room.orders.db.usecases

import com.chrrissoft.room.orders.db.objects.OrderWithRelationship
import com.chrrissoft.room.orders.db.entities.OrderDao
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.app.ResState.Success
import com.chrrissoft.room.utils.FlowUtils.ResFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveOrdersUseCase @Inject constructor(private val dao: OrderDao) {
    operator fun invoke(data: List<OrderWithRelationship>): Flow<ResState<Any>> {
        return ResFlow {
            dao.insert(data.map { it.order.copy(cityId = it.city.id) })
            emit(Success(data = 0))
        }
    }

    operator fun invoke(vararg data: OrderWithRelationship) : Flow<ResState<Any>> {
        return invoke(data.toList())
    }
}
