package com.chrrissoft.room.sellers.db.usecases

import com.chrrissoft.room.sellers.db.objects.Seller
import com.chrrissoft.room.sellers.db.entities.SellerDao
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.utils.FlowUtils.ResFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteSellersUseCase @Inject constructor(private val dao: SellerDao) {
    operator fun invoke(data: List<Seller>): Flow<ResState<Any>> {
        return ResFlow { dao.delete(data) }
    }

    operator fun invoke(vararg data: Seller) : Flow<ResState<Any>> {
        return invoke(data.toList())
    }
}
