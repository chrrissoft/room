package com.chrrissoft.room.sales.db.usecases

import com.chrrissoft.room.sales.db.objects.SaleWithRelationship
import com.chrrissoft.room.sales.db.entities.SaleDao
import com.chrrissoft.room.sales.db.objects.SaleWithNestedRelationship
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.app.ResState.Success
import com.chrrissoft.room.utils.FlowUtils.ResFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSalesUseCase @Inject constructor(private val dao: SaleDao) {
    operator fun invoke(): Flow<ResState<Map<String, SaleWithRelationship>>> {
        return ResFlow {
            dao.get().collect { data -> emit(Success(data.associateBy { it.sale.id })) }
        }
    }

    operator fun invoke(id: String): Flow<ResState<Pair<String, SaleWithNestedRelationship>>> {
        return ResFlow { dao.get(id).collect { emit(Success(it.sale.id to it)) } }
    }
}
