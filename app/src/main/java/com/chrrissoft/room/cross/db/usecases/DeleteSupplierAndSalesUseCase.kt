package com.chrrissoft.room.cross.db.usecases

import com.chrrissoft.room.cross.db.entities.SupplierAndSalesDao
import com.chrrissoft.room.cross.db.objects.SuppliersAndSales
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.utils.FlowUtils
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteSupplierAndSalesUseCase @Inject constructor(
    private val dao: SupplierAndSalesDao
) {
    operator fun invoke(data: List<SuppliersAndSales>) : Flow<ResState<Any>> {
        return FlowUtils.ResFlow { dao.delete(data) }
    }

    operator fun invoke(vararg data: SuppliersAndSales) : Flow<ResState<Any>> {
        return invoke(data.toList())
    }
}
