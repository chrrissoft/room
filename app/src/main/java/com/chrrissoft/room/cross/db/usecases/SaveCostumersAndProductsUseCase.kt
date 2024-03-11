package com.chrrissoft.room.cross.db.usecases

import com.chrrissoft.room.cross.db.entities.CostumersAndProductsDao
import com.chrrissoft.room.cross.db.objects.CostumersAndProducts
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.utils.FlowUtils
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveCostumersAndProductsUseCase @Inject constructor(
    private val dao: CostumersAndProductsDao
) {
    operator fun invoke(data: List<CostumersAndProducts>) : Flow<ResState<Any>> {
        return FlowUtils.ResFlow { dao.insert(data) }
    }

    operator fun invoke(vararg data: CostumersAndProducts) : Flow<ResState<Any>> {
        return invoke(data.toList())
    }
}
