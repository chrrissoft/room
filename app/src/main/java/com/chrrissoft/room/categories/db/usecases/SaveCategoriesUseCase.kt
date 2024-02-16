package com.chrrissoft.room.categories.db.usecases

import com.chrrissoft.room.categories.db.objects.CategoryWithRelationship
import com.chrrissoft.room.categories.db.entities.CategoryDao
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.app.ResState.Success
import com.chrrissoft.room.utils.FlowUtils.ResFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveCategoriesUseCase @Inject constructor(private val dao: CategoryDao) {
    operator fun invoke(data: List<CategoryWithRelationship>): Flow<ResState<Any>> {
        return ResFlow {
            dao.insert(data.map { it.category })
            emit(Success(data = 0))
        }
    }

    operator fun invoke(vararg data: CategoryWithRelationship) : Flow<ResState<Any>> {
        return invoke(data.toList())
    }
}
