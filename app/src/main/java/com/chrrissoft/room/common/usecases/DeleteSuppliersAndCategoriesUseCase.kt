package com.chrrissoft.room.common.usecases

import com.chrrissoft.room.common.entities.SuppliersAndCategoriesDao
import com.chrrissoft.room.common.objects.SuppliersAndCategories
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.utils.FlowUtils
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteSuppliersAndCategoriesUseCase @Inject constructor(
    private val dao: SuppliersAndCategoriesDao
) {
    operator fun invoke(data: List<SuppliersAndCategories>) : Flow<ResState<Any>> {
        return FlowUtils.ResFlow { dao.delete(data) }
    }

    operator fun invoke(vararg data: SuppliersAndCategories) : Flow<ResState<Any>> {
        return invoke(data.toList())
    }
}
