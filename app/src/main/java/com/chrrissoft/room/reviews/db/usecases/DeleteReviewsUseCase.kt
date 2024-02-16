package com.chrrissoft.room.reviews.db.usecases

import com.chrrissoft.room.reviews.db.objects.Review
import com.chrrissoft.room.reviews.db.entities.ReviewDao
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.utils.FlowUtils.ResFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteReviewsUseCase @Inject constructor(private val dao: ReviewDao) {
    operator fun invoke(data: List<Review>): Flow<ResState<Any>> {
        return ResFlow { dao.delete(data) }
    }

    operator fun invoke(vararg data: Review) : Flow<ResState<Any>> {
        return invoke(data.toList())
    }
}
