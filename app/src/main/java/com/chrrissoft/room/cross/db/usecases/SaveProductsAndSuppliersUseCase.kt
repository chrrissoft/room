package com.chrrissoft.room.cross.db.usecases

import com.chrrissoft.room.cross.db.entities.ProductsAndSuppliersDao
import com.chrrissoft.room.cross.db.objects.ProductsAndSuppliers
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.utils.FlowUtils
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveProductsAndSuppliersUseCase @Inject constructor(
    private val dao: ProductsAndSuppliersDao
) {
    operator fun invoke(data: List<ProductsAndSuppliers>) : Flow<ResState<Any>> {
        return FlowUtils.ResFlow { dao.insert(data) }
    }

    operator fun invoke(vararg data: ProductsAndSuppliers) : Flow<ResState<Any>> {
        return invoke(data.toList())
    }
}
