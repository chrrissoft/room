package com.chrrissoft.room.sellers.db.usecases

import com.chrrissoft.room.sellers.db.objects.SellerWithRelationship
import com.chrrissoft.room.sellers.db.entities.SellerDao
import com.chrrissoft.room.sellers.db.objects.SellerWithNestedRelationship
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.app.ResState.Success
import com.chrrissoft.room.utils.FlowUtils.ResFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSellersUseCase @Inject constructor(private val dao: SellerDao) {
    operator fun invoke(): Flow<ResState<Map<String, SellerWithRelationship>>> {
        return ResFlow {
            dao.get().collect { data -> emit(Success(data.associateBy { it.seller.id })) }
        }
    }

    operator fun invoke(id: String): Flow<ResState<Pair<String, SellerWithNestedRelationship>>> {
        return ResFlow { dao.get(id).collect { emit(Success(it.seller.id to it)) } }
    }
}
