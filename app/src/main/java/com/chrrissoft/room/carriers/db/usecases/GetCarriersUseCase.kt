package com.chrrissoft.room.carriers.db.usecases

import com.chrrissoft.room.carriers.db.objects.CarrierWithRelationship
import com.chrrissoft.room.carriers.db.entities.CarrierDao
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.app.ResState.Success
import com.chrrissoft.room.utils.FlowUtils.ResFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCarriersUseCase @Inject constructor(private val dao: CarrierDao) {
    operator fun invoke(): Flow<ResState<Map<String, CarrierWithRelationship>>> {
        return ResFlow {
            dao.get().collect { data -> emit(Success(data.associateBy { it.carrier.id })) }
        }
    }

    operator fun invoke(id: String): Flow<ResState<Pair<String, CarrierWithRelationship>>> {
        return ResFlow { dao.get(id).collect { emit(Success(it.carrier.id to it)) } }
    }
}
