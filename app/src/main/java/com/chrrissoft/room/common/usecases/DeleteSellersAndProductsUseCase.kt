package com.chrrissoft.room.common.usecases

import com.chrrissoft.room.common.entities.SellersAndProductsDao
import com.chrrissoft.room.common.objects.SellersAndProducts
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.utils.FlowUtils
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteSellersAndProductsUseCase @Inject constructor(
    private val dao: SellersAndProductsDao
) {
    operator fun invoke(data: List<SellersAndProducts>) : Flow<ResState<Any>> {
        return FlowUtils.ResFlow { dao.delete(data) }
    }

    operator fun invoke(vararg data: SellersAndProducts) : Flow<ResState<Any>> {
        return invoke(data.toList())
    }
}
