package com.chrrissoft.room.common.usecases

import com.chrrissoft.room.common.entities.SupplierAndSalesDao
import com.chrrissoft.room.common.objects.SupplierAndSales
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.utils.FlowUtils
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveSupplierAndSalesUseCase @Inject constructor(
    private val dao: SupplierAndSalesDao
) {
    operator fun invoke(data: List<SupplierAndSales>) : Flow<ResState<Any>> {
        return FlowUtils.ResFlow { dao.insert(data) }
    }

    operator fun invoke(vararg data: SupplierAndSales) : Flow<ResState<Any>> {
        return invoke(data.toList())
    }
}
