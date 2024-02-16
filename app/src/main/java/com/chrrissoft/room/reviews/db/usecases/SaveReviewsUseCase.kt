package com.chrrissoft.room.reviews.db.usecases

import com.chrrissoft.room.reviews.db.objects.ReviewWithRelationship
import com.chrrissoft.room.reviews.db.entities.ReviewDao
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.app.ResState.Success
import com.chrrissoft.room.utils.FlowUtils.ResFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveReviewsUseCase @Inject constructor(private val dao: ReviewDao) {
    operator fun invoke(data: List<ReviewWithRelationship>): Flow<ResState<Any>> {
        return ResFlow {
            dao.insert(data.map { it.review })
            emit(Success(data = 0))
        }
    }

    operator fun invoke(vararg data: ReviewWithRelationship) : Flow<ResState<Any>> {
        return invoke(data.toList())
    }
}
