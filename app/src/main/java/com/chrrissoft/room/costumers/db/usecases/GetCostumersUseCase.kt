package com.chrrissoft.room.costumers.db.usecases

import com.chrrissoft.room.costumers.db.objects.CostumerWithRelationship
import com.chrrissoft.room.costumers.db.entities.CostumerDao
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.app.ResState.Success
import com.chrrissoft.room.utils.FlowUtils.ResFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCostumersUseCase @Inject constructor(private val dao: CostumerDao) {
    operator fun invoke(): Flow<ResState<Map<String, CostumerWithRelationship>>> {
        return ResFlow {
            dao.get().collect { data -> emit(Success(data.associateBy { it.costumer.id })) }
        }
    }

    operator fun invoke(id: String): Flow<ResState<Pair<String, CostumerWithRelationship>>> {
        return ResFlow { dao.get(id).collect { emit(Success(it.costumer.id to it)) } }
    }
}
