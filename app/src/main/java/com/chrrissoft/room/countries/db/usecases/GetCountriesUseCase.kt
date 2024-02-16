package com.chrrissoft.room.countries.db.usecases

import com.chrrissoft.room.countries.db.objects.CountryWithRelationship
import com.chrrissoft.room.countries.db.entities.CountryDao
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.app.ResState.Success
import com.chrrissoft.room.utils.FlowUtils.ResFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCountriesUseCase @Inject constructor(private val dao: CountryDao) {
    operator fun invoke(): Flow<ResState<Map<String, CountryWithRelationship>>> {
        return ResFlow {
            dao.get().collect { data -> emit(Success(data.associateBy { it.country.id })) }
        }
    }

    operator fun invoke(id: String): Flow<ResState<Pair<String, CountryWithRelationship>>> {
        return ResFlow { dao.get(id).collect { emit(Success(it.country.id to it)) } }
    }
}
