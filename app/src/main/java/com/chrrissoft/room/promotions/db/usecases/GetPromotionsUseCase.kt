package com.chrrissoft.room.promotions.db.usecases

import com.chrrissoft.room.promotions.db.objects.PromotionWithRelationship
import com.chrrissoft.room.promotions.db.entities.PromotionDao
import com.chrrissoft.room.promotions.db.objects.PromotionNestedWithRelationship
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.app.ResState.Success
import com.chrrissoft.room.utils.FlowUtils.ResFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPromotionsUseCase @Inject constructor(private val dao: PromotionDao) {
    operator fun invoke(): Flow<ResState<Map<String, PromotionWithRelationship>>> {
        return ResFlow {
            dao.get().collect { data -> emit(Success(data.associateBy { it.promotion.id })) }
        }
    }

    operator fun invoke(id: String): Flow<ResState<Pair<String, PromotionNestedWithRelationship>>> {
        return ResFlow { dao.get(id).collect { emit(Success(it.promotion.id to it)) } }
    }
}
