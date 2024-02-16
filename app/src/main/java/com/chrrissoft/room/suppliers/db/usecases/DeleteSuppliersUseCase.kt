package com.chrrissoft.room.suppliers.db.usecases

import com.chrrissoft.room.suppliers.db.objects.Supplier
import com.chrrissoft.room.suppliers.db.entities.SupplierDao
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.utils.FlowUtils.ResFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteSuppliersUseCase @Inject constructor(private val dao: SupplierDao) {
    operator fun invoke(data: List<Supplier>): Flow<ResState<Any>> {
        return ResFlow { dao.delete(data) }
    }

    operator fun invoke(vararg data: Supplier) : Flow<ResState<Any>> {
        return invoke(data.toList())
    }
}
