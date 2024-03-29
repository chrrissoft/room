package com.chrrissoft.room.cross.db.usecases

import com.chrrissoft.room.cross.db.entities.CategoriesAndPromotionsDao
import com.chrrissoft.room.cross.db.objects.CategoriesAndPromotions
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.utils.FlowUtils
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteCategoriesAndPromotionsUseCase @Inject constructor(
    private val dao: CategoriesAndPromotionsDao
) {
    operator fun invoke(data: List<CategoriesAndPromotions>) : Flow<ResState<Any>> {
        return FlowUtils.ResFlow { dao.delete(data) }
    }

    operator fun invoke(vararg data: CategoriesAndPromotions) : Flow<ResState<Any>> {
        return invoke(data.toList())
    }
}
