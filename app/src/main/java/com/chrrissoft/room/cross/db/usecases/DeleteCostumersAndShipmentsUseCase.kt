package com.chrrissoft.room.cross.db.usecases

import com.chrrissoft.room.cross.db.entities.CostumersAndShipmentsDao
import com.chrrissoft.room.cross.db.objects.CostumersAndShipments
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.utils.FlowUtils
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteCostumersAndShipmentsUseCase @Inject constructor(
    private val dao: CostumersAndShipmentsDao
) {
    operator fun invoke(data: List<CostumersAndShipments>) : Flow<ResState<Any>> {
        return FlowUtils.ResFlow { dao.delete(data) }
    }

    operator fun invoke(vararg data: CostumersAndShipments) : Flow<ResState<Any>> {
        return invoke(data.toList())
    }
}
