package com.chrrissoft.room.cross.db.usecases

import com.chrrissoft.room.cross.db.entities.CategoriesAndSalesDao
import com.chrrissoft.room.cross.db.objects.CategoriesAndSales
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.utils.FlowUtils
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteCategoriesAndSalesUseCase @Inject constructor(
    private val dao: CategoriesAndSalesDao
) {
    operator fun invoke(data: List<CategoriesAndSales>) : Flow<ResState<Any>> {
        return FlowUtils.ResFlow { dao.delete(data) }
    }

    operator fun invoke(vararg data: CategoriesAndSales) : Flow<ResState<Any>> {
        return invoke(data.toList())
    }
}
