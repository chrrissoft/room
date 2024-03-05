package com.chrrissoft.room.categories.db.usecases

import com.chrrissoft.room.categories.db.objects.Category
import com.chrrissoft.room.categories.db.entities.CategoryDao
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.app.ResState.Success
import com.chrrissoft.room.utils.FlowUtils.ResFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveCategoriesUseCase @Inject constructor(private val dao: CategoryDao) {
    operator fun invoke(data: List<Category>): Flow<ResState<Any>> {
        return ResFlow { emit(Success(dao.insert(data))) }
    }

    operator fun invoke(vararg data: Category) : Flow<ResState<Any>> {
        return invoke(data.toList())
    }
}
