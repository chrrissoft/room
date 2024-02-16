package com.chrrissoft.room.countries.db.usecases

import com.chrrissoft.room.countries.db.objects.CountryWithRelationship
import com.chrrissoft.room.countries.db.entities.CountryDao
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.app.ResState.Success
import com.chrrissoft.room.utils.FlowUtils.ResFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveCountriesUseCase @Inject constructor(private val dao: CountryDao) {
    operator fun invoke(data: List<CountryWithRelationship>): Flow<ResState<Any>> {
        return ResFlow {
            dao.insert(data.map { it.country })
            emit(Success(data = 0))
        }
    }

    operator fun invoke(vararg data: CountryWithRelationship) : Flow<ResState<Any>> {
        return invoke(data.toList())
    }
}
