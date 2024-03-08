package com.chrrissoft.room.common.usecases

import com.chrrissoft.room.common.entities.CategoriesAndSalesDao
import com.chrrissoft.room.common.objects.CategoriesAndSales
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.utils.FlowUtils.ResFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveCategoriesAndSalesUseCase @Inject constructor(
    private val dao: CategoriesAndSalesDao
) {
    operator fun invoke(data: List<CategoriesAndSales>) : Flow<ResState<Any>> {
        return ResFlow { dao.insert(data) }
    }

    operator fun invoke(vararg data: CategoriesAndSales) : Flow<ResState<Any>> {
        return invoke(data.toList())
    }
}
