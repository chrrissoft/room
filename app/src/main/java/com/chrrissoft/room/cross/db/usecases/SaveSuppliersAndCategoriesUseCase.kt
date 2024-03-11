package com.chrrissoft.room.cross.db.usecases

import com.chrrissoft.room.cross.db.entities.SuppliersAndCategoriesDao
import com.chrrissoft.room.cross.db.objects.SuppliersAndCategories
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.utils.FlowUtils
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveSuppliersAndCategoriesUseCase @Inject constructor(
    private val dao: SuppliersAndCategoriesDao
) {
    operator fun invoke(data: List<SuppliersAndCategories>) : Flow<ResState<Any>> {
        return FlowUtils.ResFlow { dao.insert(data) }
    }

    operator fun invoke(vararg data: SuppliersAndCategories) : Flow<ResState<Any>> {
        return invoke(data.toList())
    }
}
