package com.chrrissoft.room.common.usecases

import com.chrrissoft.room.common.entities.PromotionsAndOrdersDao
import com.chrrissoft.room.common.objects.PromotionsAndOrders
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.utils.FlowUtils
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeletePromotionsAndOrdersUseCase @Inject constructor(
    private val dao: PromotionsAndOrdersDao
) {
    operator fun invoke(data: List<PromotionsAndOrders>) : Flow<ResState<Any>> {
        return FlowUtils.ResFlow { dao.delete(data) }
    }

    operator fun invoke(vararg data: PromotionsAndOrders) : Flow<ResState<Any>> {
        return invoke(data.toList())
    }
}
