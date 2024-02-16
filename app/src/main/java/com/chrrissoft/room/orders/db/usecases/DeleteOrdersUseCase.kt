package com.chrrissoft.room.orders.db.usecases

import com.chrrissoft.room.orders.db.objects.Order
import com.chrrissoft.room.orders.db.entities.OrderDao
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.utils.FlowUtils.ResFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteOrdersUseCase @Inject constructor(private val dao: OrderDao) {
    operator fun invoke(data: List<Order>): Flow<ResState<Any>> {
        return ResFlow { dao.delete(data) }
    }

    operator fun invoke(vararg data: Order) : Flow<ResState<Any>> {
        return invoke(data.toList())
    }
}
