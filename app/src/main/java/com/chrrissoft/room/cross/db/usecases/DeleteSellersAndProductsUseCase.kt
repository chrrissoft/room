package com.chrrissoft.room.cross.db.usecases

import com.chrrissoft.room.cross.db.entities.SellersAndProductsDao
import com.chrrissoft.room.cross.db.objects.SellersAndProducts
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.utils.FlowUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteSellersAndProductsUseCase @Inject constructor(
    private val dao: SellersAndProductsDao
) {
    operator fun invoke(): Flow<ResState<Any>> = flow { invoke(dao.get()).collect { emit(it) } }

    operator fun invoke(data: List<SellersAndProducts>) : Flow<ResState<Any>> {
        return FlowUtils.ResFlow { dao.delete(data) }
    }

    operator fun invoke(vararg data: SellersAndProducts) : Flow<ResState<Any>> {
        return invoke(data.toList())
    }
}
