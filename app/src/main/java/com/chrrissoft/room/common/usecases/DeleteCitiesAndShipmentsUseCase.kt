package com.chrrissoft.room.common.usecases

import com.chrrissoft.room.common.entities.CitiesAndShipmentsDao
import com.chrrissoft.room.common.objects.CitiesAndShipments
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.utils.FlowUtils
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteCitiesAndShipmentsUseCase @Inject constructor(
    private val dao: CitiesAndShipmentsDao
) {
    operator fun invoke(data: List<CitiesAndShipments>) : Flow<ResState<Any>> {
        return FlowUtils.ResFlow { dao.delete(data) }
    }

    operator fun invoke(vararg data: CitiesAndShipments) : Flow<ResState<Any>> {
        return invoke(data.toList())
    }
}
