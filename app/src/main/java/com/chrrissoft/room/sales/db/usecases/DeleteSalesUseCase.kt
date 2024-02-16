package com.chrrissoft.room.sales.db.usecases

import com.chrrissoft.room.sales.db.objects.Sale
import com.chrrissoft.room.sales.db.entities.SaleDao
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.utils.FlowUtils.ResFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteSalesUseCase @Inject constructor(private val dao: SaleDao) {
    operator fun invoke(data: List<Sale>): Flow<ResState<Any>> {
        return ResFlow { dao.delete(data) }
    }

    operator fun invoke(vararg data: Sale) : Flow<ResState<Any>> {
        return invoke(data.toList())
    }
}
