package com.chrrissoft.room.sellers.db.usecases

import com.chrrissoft.room.sellers.db.objects.SellerWithRelationship
import com.chrrissoft.room.sellers.db.entities.SellerDao
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.app.ResState.Success
import com.chrrissoft.room.utils.FlowUtils.ResFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveSellersUseCase @Inject constructor(private val dao: SellerDao) {
    operator fun invoke(data: List<SellerWithRelationship>): Flow<ResState<Any>> {
        return ResFlow {
            dao.insert(data.map { it.seller })
            emit(Success(data = 0))
        }
    }

    operator fun invoke(vararg data: SellerWithRelationship) : Flow<ResState<Any>> {
        return invoke(data.toList())
    }
}
