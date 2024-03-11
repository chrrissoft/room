package com.chrrissoft.room.cross.db.usecases

import com.chrrissoft.room.cross.db.entities.SellersAndShipmentsDao
import com.chrrissoft.room.cross.db.objects.SellersAndShipments
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.utils.FlowUtils
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteSellersAndShipmentsUseCase @Inject constructor(
    private val dao: SellersAndShipmentsDao
) {
    operator fun invoke(data: List<SellersAndShipments>) : Flow<ResState<Any>> {
        return FlowUtils.ResFlow { dao.delete(data) }
    }

    operator fun invoke(vararg data: SellersAndShipments) : Flow<ResState<Any>> {
        return invoke(data.toList())
    }
}
