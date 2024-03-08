package com.chrrissoft.room.common.usecases

import com.chrrissoft.room.common.entities.ProductsAndShipmentsDao
import com.chrrissoft.room.common.objects.ProductsAndShipments
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.utils.FlowUtils
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveProductsAndShipmentsUseCase @Inject constructor(
    private val dao: ProductsAndShipmentsDao
) {
    operator fun invoke(data: List<ProductsAndShipments>) : Flow<ResState<Any>> {
        return FlowUtils.ResFlow { dao.insert(data) }
    }

    operator fun invoke(vararg data: ProductsAndShipments) : Flow<ResState<Any>> {
        return invoke(data.toList())
    }
}
