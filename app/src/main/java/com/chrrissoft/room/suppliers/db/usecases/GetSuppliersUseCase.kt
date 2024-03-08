package com.chrrissoft.room.suppliers.db.usecases

import com.chrrissoft.room.suppliers.db.objects.SupplierWithRelationship
import com.chrrissoft.room.suppliers.db.entities.SupplierDao
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.app.ResState.Success
import com.chrrissoft.room.suppliers.db.objects.SupplierWithNestedRelationship
import com.chrrissoft.room.utils.FlowUtils.ResFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSuppliersUseCase @Inject constructor(private val dao: SupplierDao) {
    operator fun invoke(): Flow<ResState<Map<String, SupplierWithRelationship>>> {
        return ResFlow {
            dao.get().collect { data -> emit(Success(data.associateBy { it.supplier.id })) }
        }
    }

    operator fun invoke(id: String): Flow<ResState<Pair<String, SupplierWithNestedRelationship>>> {
        return ResFlow { dao.get(id).collect { emit(Success(it.supplier.id to it)) } }
    }
}
