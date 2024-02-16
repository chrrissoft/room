package com.chrrissoft.room.products.db.usecases

import com.chrrissoft.room.products.db.objects.ProductWithRelationship
import com.chrrissoft.room.products.db.entities.ProductDao
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.app.ResState.Success
import com.chrrissoft.room.utils.FlowUtils.ResFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveProductsUseCase @Inject constructor(private val dao: ProductDao) {
    operator fun invoke(data: List<ProductWithRelationship>): Flow<ResState<Any>> {
        return ResFlow {
            dao.insert(data.map { it.product })
            emit(Success(data = 0))
        }
    }

    operator fun invoke(vararg data: ProductWithRelationship) : Flow<ResState<Any>> {
        return invoke(data.toList())
    }
}
