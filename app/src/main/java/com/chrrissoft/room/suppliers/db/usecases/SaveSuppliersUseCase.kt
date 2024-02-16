package com.chrrissoft.room.suppliers.db.usecases

import com.chrrissoft.room.suppliers.db.objects.SupplierWithRelationship
import com.chrrissoft.room.suppliers.db.entities.SupplierDao
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.app.ResState.Success
import com.chrrissoft.room.utils.FlowUtils.ResFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveSuppliersUseCase @Inject constructor(private val dao: SupplierDao) {
    operator fun invoke(data: List<SupplierWithRelationship>): Flow<ResState<Any>> {
        return ResFlow {
            dao.insert(data.map { it.supplier })
            emit(Success(data = 0))
        }
    }

    operator fun invoke(vararg data: SupplierWithRelationship) : Flow<ResState<Any>> {
        return invoke(data.toList())
    }
}
