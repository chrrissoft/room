package com.chrrissoft.room.carriers.db.usecases

import com.chrrissoft.room.carriers.db.objects.Carrier
import com.chrrissoft.room.carriers.db.entities.CarrierDao
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.utils.FlowUtils.ResFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteCarriersUseCase @Inject constructor(private val dao: CarrierDao) {
    operator fun invoke(data: List<Carrier>): Flow<ResState<Any>> {
        return ResFlow { dao.delete(data) }
    }

    operator fun invoke(vararg data: Carrier) : Flow<ResState<Any>> {
        return invoke(data.toList())
    }
}
