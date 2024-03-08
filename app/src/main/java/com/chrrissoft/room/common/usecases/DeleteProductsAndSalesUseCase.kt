package com.chrrissoft.room.common.usecases

import com.chrrissoft.room.common.entities.ProductsAndSalesDao
import com.chrrissoft.room.common.objects.ProductsAndSales
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.utils.FlowUtils
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteProductsAndSalesUseCase @Inject constructor(
    private val dao: ProductsAndSalesDao
) {
    operator fun invoke(data: List<ProductsAndSales>) : Flow<ResState<Any>> {
        return FlowUtils.ResFlow { dao.delete(data) }
    }

    operator fun invoke(vararg data: ProductsAndSales) : Flow<ResState<Any>> {
        return invoke(data.toList())
    }
}
