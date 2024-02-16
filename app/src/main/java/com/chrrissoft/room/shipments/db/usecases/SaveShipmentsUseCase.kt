package com.chrrissoft.room.shipments.db.usecases

import com.chrrissoft.room.shipments.db.objects.ShippingWithRelationship
import com.chrrissoft.room.shipments.db.entities.ShippingDao
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.app.ResState.Success
import com.chrrissoft.room.utils.FlowUtils.ResFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveShipmentsUseCase @Inject constructor(private val dao: ShippingDao) {
    operator fun invoke(data: List<ShippingWithRelationship>): Flow<ResState<Any>> {
        return ResFlow {
            dao.insert(data.map { it.shipping })
            emit(Success(data = 0))
        }
    }

    operator fun invoke(vararg data: ShippingWithRelationship) : Flow<ResState<Any>> {
        return invoke(data.toList())
    }
}
