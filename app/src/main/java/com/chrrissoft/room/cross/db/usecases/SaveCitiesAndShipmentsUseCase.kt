package com.chrrissoft.room.cross.db.usecases

import com.chrrissoft.room.cross.db.entities.CitiesAndShipmentsDao
import com.chrrissoft.room.cross.db.objects.CitiesAndShipments
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.utils.FlowUtils
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveCitiesAndShipmentsUseCase @Inject constructor(
    private val dao: CitiesAndShipmentsDao
) {
    operator fun invoke(data: List<CitiesAndShipments>) : Flow<ResState<Any>> {
        return FlowUtils.ResFlow { dao.insert(data) }
    }

    operator fun invoke(vararg data: CitiesAndShipments) : Flow<ResState<Any>> {
        return invoke(data.toList())
    }
}
