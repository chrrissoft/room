package com.chrrissoft.room.cross.db.usecases

import com.chrrissoft.room.cross.db.entities.ProductsAndSalesDao
import com.chrrissoft.room.cross.db.objects.ProductsAndSales
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.utils.FlowUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteProductsAndSalesUseCase @Inject constructor(
    private val dao: ProductsAndSalesDao
) {
    operator fun invoke(): Flow<ResState<Any>> = flow { invoke(dao.get()).collect { emit(it) } }

    operator fun invoke(data: List<ProductsAndSales>): Flow<ResState<Any>> {
        return FlowUtils.ResFlow { dao.delete(data) }
    }

    operator fun invoke(vararg data: ProductsAndSales): Flow<ResState<Any>> {
        return invoke(data.toList())
    }
}
