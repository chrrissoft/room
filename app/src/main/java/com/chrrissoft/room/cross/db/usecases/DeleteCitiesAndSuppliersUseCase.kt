package com.chrrissoft.room.cross.db.usecases

import com.chrrissoft.room.cross.db.entities.CitiesAndSuppliersDao
import com.chrrissoft.room.cross.db.objects.CitiesAndSuppliers
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.utils.FlowUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteCitiesAndSuppliersUseCase @Inject constructor(
    private val dao: CitiesAndSuppliersDao
) {
    operator fun invoke(): Flow<ResState<Any>> = flow { invoke(dao.get()).collect { emit(it) } }

    operator fun invoke(data: List<CitiesAndSuppliers>): Flow<ResState<Any>> {
        return FlowUtils.ResFlow { dao.delete(data) }
    }

    operator fun invoke(vararg data: CitiesAndSuppliers): Flow<ResState<Any>> {
        return invoke(data.toList())
    }
}
