package com.chrrissoft.room.common.usecases

import com.chrrissoft.room.common.entities.CategoriesAndOrdersDao
import com.chrrissoft.room.common.objects.CategoriesAndOrders
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.utils.FlowUtils
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveCategoriesAndOrdersUseCase @Inject constructor(
    private val dao: CategoriesAndOrdersDao
) {
    operator fun invoke(data: List<CategoriesAndOrders>) : Flow<ResState<Any>> {
        return FlowUtils.ResFlow { dao.insert(data) }
    }

    operator fun invoke(vararg data: CategoriesAndOrders) : Flow<ResState<Any>> {
        return invoke(data.toList())
    }
}
