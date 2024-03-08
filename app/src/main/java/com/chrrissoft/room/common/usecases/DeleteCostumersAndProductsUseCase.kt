package com.chrrissoft.room.common.usecases

import com.chrrissoft.room.common.entities.CostumersAndProductsDao
import com.chrrissoft.room.common.objects.CostumersAndProducts
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.utils.FlowUtils
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteCostumersAndProductsUseCase @Inject constructor(
    private val dao: CostumersAndProductsDao
) {
    operator fun invoke(data: List<CostumersAndProducts>) : Flow<ResState<Any>> {
        return FlowUtils.ResFlow { dao.delete(data) }
    }

    operator fun invoke(vararg data: CostumersAndProducts) : Flow<ResState<Any>> {
        return invoke(data.toList())
    }
}
