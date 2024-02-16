package com.chrrissoft.room.inventories.db.usecases

import com.chrrissoft.room.inventories.db.objects.InventoryWithRelationship
import com.chrrissoft.room.inventories.db.entities.InventoryDao
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.app.ResState.Success
import com.chrrissoft.room.utils.FlowUtils.ResFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveInventoriesUseCase @Inject constructor(private val dao: InventoryDao) {
    operator fun invoke(data: List<InventoryWithRelationship>): Flow<ResState<Any>> {
        return ResFlow {
            dao.insert(data.map { it.inventory })
            emit(Success(data = 0))
        }
    }

    operator fun invoke(vararg data: InventoryWithRelationship) : Flow<ResState<Any>> {
        return invoke(data.toList())
    }
}
