package com.chrrissoft.room.products.db.usecases

import com.chrrissoft.room.products.db.objects.ProductWithRelationship
import com.chrrissoft.room.products.db.entities.ProductDao
import com.chrrissoft.room.products.db.objects.ProductWithNestedRelationship
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.app.ResState.Success
import com.chrrissoft.room.utils.FlowUtils.ResFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(private val dao: ProductDao) {
    operator fun invoke(): Flow<ResState<Map<String, ProductWithRelationship>>> {
        return ResFlow {
            dao.get().collect { data -> emit(Success(data.associateBy { it.product.id })) }
        }
    }

    operator fun invoke(id: String): Flow<ResState<Pair<String, ProductWithNestedRelationship>>> {
        return ResFlow { dao.get(id).collect { emit(Success(it.product.id to it)) } }
    }
}
