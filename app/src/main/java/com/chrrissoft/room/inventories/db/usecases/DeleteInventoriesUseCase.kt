package com.chrrissoft.room.inventories.db.usecases

import com.chrrissoft.room.inventories.db.objects.Inventory
import com.chrrissoft.room.inventories.db.entities.InventoryDao
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.utils.FlowUtils.ResFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteInventoriesUseCase @Inject constructor(private val dao: InventoryDao) {
    operator fun invoke(data: List<Inventory>): Flow<ResState<Any>> {
        return ResFlow { dao.delete(data) }
    }

    operator fun invoke(vararg data: Inventory) : Flow<ResState<Any>> {
        return invoke(data.toList())
    }
}
