package com.chrrissoft.room.inventories.db.usecases

import com.chrrissoft.room.inventories.db.objects.InventoryWithRelationship
import com.chrrissoft.room.inventories.db.entities.InventoryDao
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.app.ResState.Success
import com.chrrissoft.room.utils.FlowUtils.ResFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetInventoriesUseCase @Inject constructor(private val dao: InventoryDao) {
    operator fun invoke(): Flow<ResState<Map<String, InventoryWithRelationship>>> {
        return ResFlow {
            dao.get().collect { data -> emit(Success(data.associateBy { it.inventory.productId })) }
        }
    }

    operator fun invoke(id: String): Flow<ResState<Pair<String, InventoryWithRelationship>>> {
        return ResFlow { dao.get(id).collect { emit(Success(it.inventory.productId to it)) } }
    }
}
