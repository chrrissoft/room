package com.chrrissoft.room.categories.db.usecases

import com.chrrissoft.room.categories.db.objects.CategoryWithRelationship
import com.chrrissoft.room.categories.db.entities.CategoryDao
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.app.ResState.Success
import com.chrrissoft.room.utils.FlowUtils.ResFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(private val dao: CategoryDao) {
    operator fun invoke(): Flow<ResState<Map<String, CategoryWithRelationship>>> {
        return ResFlow {
            dao.get().collect { data -> emit(Success(data.associateBy { it.category.id })) }
        }
    }

    operator fun invoke(id: String): Flow<ResState<Pair<String, CategoryWithRelationship>>> {
        return ResFlow { dao.get(id).collect { emit(Success(it.category.id to it)) } }
    }
}
