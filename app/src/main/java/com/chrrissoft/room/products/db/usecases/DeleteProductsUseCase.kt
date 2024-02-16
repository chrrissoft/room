package com.chrrissoft.room.products.db.usecases

import com.chrrissoft.room.products.db.objects.Product
import com.chrrissoft.room.products.db.entities.ProductDao
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.utils.FlowUtils.ResFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteProductsUseCase @Inject constructor(private val dao: ProductDao) {
    operator fun invoke(data: List<Product>): Flow<ResState<Any>> {
        return ResFlow { dao.delete(data) }
    }

    operator fun invoke(vararg data: Product) : Flow<ResState<Any>> {
        return invoke(data.toList())
    }
}
