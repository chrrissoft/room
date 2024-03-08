package com.chrrissoft.room.common.usecases

import com.chrrissoft.room.common.entities.SalesAndPromotionsDao
import com.chrrissoft.room.common.objects.SalesAndPromotions
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.utils.FlowUtils
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteSalesAndPromotionsUseCase @Inject constructor(
    private val dao: SalesAndPromotionsDao
) {
    operator fun invoke(data: List<SalesAndPromotions>) : Flow<ResState<Any>> {
        return FlowUtils.ResFlow { dao.delete(data) }
    }

    operator fun invoke(vararg data: SalesAndPromotions) : Flow<ResState<Any>> {
        return invoke(data.toList())
    }
}
