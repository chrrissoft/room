package com.chrrissoft.room.costumers.db.usecases

import com.chrrissoft.room.costumers.db.objects.Costumer
import com.chrrissoft.room.costumers.db.entities.CostumerDao
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.utils.FlowUtils.ResFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteCostumersUseCase @Inject constructor(private val dao: CostumerDao) {
    operator fun invoke(data: List<Costumer>): Flow<ResState<Any>> {
        return ResFlow { dao.delete(data) }
    }

    operator fun invoke(vararg data: Costumer) : Flow<ResState<Any>> {
        return invoke(data.toList())
    }
}
