package com.chrrissoft.room.promotions.db.usecases

import com.chrrissoft.room.promotions.db.objects.PromotionWithRelationship
import com.chrrissoft.room.promotions.db.entities.PromotionDao
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.app.ResState.Success
import com.chrrissoft.room.utils.FlowUtils.ResFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SavePromotionsUseCase @Inject constructor(private val dao: PromotionDao) {
    operator fun invoke(data: List<PromotionWithRelationship>): Flow<ResState<Any>> {
        return ResFlow {
            dao.insert(data.map { it.promotion })
            emit(Success(data = 0))
        }
    }

    operator fun invoke(vararg data: PromotionWithRelationship) : Flow<ResState<Any>> {
        return invoke(data.toList())
    }
}
