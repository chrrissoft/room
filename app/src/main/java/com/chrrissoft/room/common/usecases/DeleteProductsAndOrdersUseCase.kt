package com.chrrissoft.room.common.usecases

import com.chrrissoft.room.common.entities.ProductsAndOrdersDao
import com.chrrissoft.room.common.objects.ProductsAndOrders
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.utils.FlowUtils
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteProductsAndOrdersUseCase @Inject constructor(
    private val dao: ProductsAndOrdersDao
) {
    operator fun invoke(data: List<ProductsAndOrders>) : Flow<ResState<Any>> {
        return FlowUtils.ResFlow { dao.delete(data) }
    }

    operator fun invoke(vararg data: ProductsAndOrders) : Flow<ResState<Any>> {
        return invoke(data.toList())
    }
}
