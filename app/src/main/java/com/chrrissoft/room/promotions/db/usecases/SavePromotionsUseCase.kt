package com.chrrissoft.room.promotions.db.usecases

import com.chrrissoft.room.promotions.db.objects.Promotion
import com.chrrissoft.room.promotions.db.entities.PromotionDao
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.app.ResState.Success
import com.chrrissoft.room.utils.FlowUtils.ResFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SavePromotionsUseCase @Inject constructor(private val dao: PromotionDao) {
    operator fun invoke(data: List<Promotion>): Flow<ResState<Any>> {
        return ResFlow { emit(Success(dao.insert(data))) }
    }

    operator fun invoke(vararg data: Promotion) : Flow<ResState<Any>> {
        return invoke(data.toList())
    }
}
