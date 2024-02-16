package com.chrrissoft.room.reviews.db.usecases

import com.chrrissoft.room.reviews.db.objects.ReviewWithRelationship
import com.chrrissoft.room.reviews.db.entities.ReviewDao
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.app.ResState.Success
import com.chrrissoft.room.utils.FlowUtils.ResFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetReviewsUseCase @Inject constructor(private val dao: ReviewDao) {
    operator fun invoke(): Flow<ResState<Map<String, ReviewWithRelationship>>> {
        return ResFlow {
            dao.get().collect { data -> emit(Success(data.associateBy { it.review.id })) }
        }
    }

    operator fun invoke(id: String): Flow<ResState<Pair<String, ReviewWithRelationship>>> {
        return ResFlow { dao.get(id).collect { emit(Success(it.review.id to it)) } }
    }
}
