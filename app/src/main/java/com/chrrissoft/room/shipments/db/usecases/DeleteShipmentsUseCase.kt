package com.chrrissoft.room.shipments.db.usecases

import com.chrrissoft.room.shipments.db.objects.Shipping
import com.chrrissoft.room.shipments.db.entities.ShippingDao
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.utils.FlowUtils.ResFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteShipmentsUseCase @Inject constructor(private val dao: ShippingDao) {
    operator fun invoke(data: List<Shipping>): Flow<ResState<Any>> {
        return ResFlow { dao.delete(data) }
    }

    operator fun invoke(vararg data: Shipping) : Flow<ResState<Any>> {
        return invoke(data.toList())
    }
}
