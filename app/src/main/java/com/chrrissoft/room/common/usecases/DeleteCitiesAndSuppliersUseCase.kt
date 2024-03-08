package com.chrrissoft.room.common.usecases

import com.chrrissoft.room.common.entities.CitiesAndSuppliersDao
import com.chrrissoft.room.common.objects.CitiesAndSuppliers
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.utils.FlowUtils
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteCitiesAndSuppliersUseCase @Inject constructor(
    private val dao: CitiesAndSuppliersDao
) {
    operator fun invoke(data: List<CitiesAndSuppliers>) : Flow<ResState<Any>> {
        return FlowUtils.ResFlow { dao.delete(data) }
    }

    operator fun invoke(vararg data: CitiesAndSuppliers) : Flow<ResState<Any>> {
        return invoke(data.toList())
    }
}
