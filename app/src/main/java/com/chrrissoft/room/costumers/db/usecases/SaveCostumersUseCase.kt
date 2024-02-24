package com.chrrissoft.room.costumers.db.usecases

import com.chrrissoft.room.costumers.db.objects.CostumerWithRelationship
import com.chrrissoft.room.costumers.db.entities.CostumerDao
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.app.ResState.Success
import com.chrrissoft.room.utils.FlowUtils.ResFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveCostumersUseCase @Inject constructor(private val dao: CostumerDao) {
    operator fun invoke(data: List<CostumerWithRelationship>): Flow<ResState<Any>> {
        return ResFlow {
            dao.insert(data.map { it.costumer.copy(cityId = it.city.id) })
            emit(Success(data = 0))
        }
    }

    operator fun invoke(vararg data: CostumerWithRelationship) : Flow<ResState<Any>> {
        return invoke(data.toList())
    }
}
