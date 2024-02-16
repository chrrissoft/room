package com.chrrissoft.room.carriers.db.usecases

import com.chrrissoft.room.carriers.db.objects.CarrierWithRelationship
import com.chrrissoft.room.carriers.db.entities.CarrierDao
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.app.ResState.Success
import com.chrrissoft.room.utils.FlowUtils.ResFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveCarriersUseCase @Inject constructor(private val dao: CarrierDao) {
    operator fun invoke(data: List<CarrierWithRelationship>): Flow<ResState<Any>> {
        return ResFlow {
            dao.insert(data.map { it.carrier })
            emit(Success(data = 0))
        }
    }

    operator fun invoke(vararg data: CarrierWithRelationship) : Flow<ResState<Any>> {
        return invoke(data.toList())
    }
}
