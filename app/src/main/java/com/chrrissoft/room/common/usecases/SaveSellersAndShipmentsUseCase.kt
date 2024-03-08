package com.chrrissoft.room.common.usecases

import com.chrrissoft.room.common.entities.SellersAndShipmentsDao
import com.chrrissoft.room.common.objects.SellersAndShipments
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.utils.FlowUtils
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveSellersAndShipmentsUseCase @Inject constructor(
    private val dao: SellersAndShipmentsDao
) {
    operator fun invoke(data: List<SellersAndShipments>) : Flow<ResState<Any>> {
        return FlowUtils.ResFlow { dao.insert(data) }
    }

    operator fun invoke(vararg data: SellersAndShipments) : Flow<ResState<Any>> {
        return invoke(data.toList())
    }
}
