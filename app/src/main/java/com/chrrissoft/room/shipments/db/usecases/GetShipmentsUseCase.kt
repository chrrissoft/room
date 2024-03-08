package com.chrrissoft.room.shipments.db.usecases

import com.chrrissoft.room.shipments.db.objects.ShippingWithRelationship
import com.chrrissoft.room.shipments.db.entities.ShippingDao
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.app.ResState.Success
import com.chrrissoft.room.shipments.db.objects.ShippingWithNestedRelationship
import com.chrrissoft.room.utils.FlowUtils.ResFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetShipmentsUseCase @Inject constructor(private val dao: ShippingDao) {
    operator fun invoke(): Flow<ResState<Map<String, ShippingWithRelationship>>> {
        return ResFlow {
            dao.get().collect { data -> emit(Success(data.associateBy { it.shipping.id })) }
        }
    }

    operator fun invoke(id: String): Flow<ResState<Pair<String, ShippingWithNestedRelationship>>> {
        return ResFlow { dao.get(id).collect { emit(Success(it.shipping.id to it)) } }
    }
}
