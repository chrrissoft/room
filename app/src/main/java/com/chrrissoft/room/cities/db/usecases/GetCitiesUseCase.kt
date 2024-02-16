package com.chrrissoft.room.cities.db.usecases

import com.chrrissoft.room.cities.db.objects.CityWithRelationship
import com.chrrissoft.room.cities.db.entities.CityDao
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.app.ResState.Success
import com.chrrissoft.room.utils.FlowUtils.ResFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCitiesUseCase @Inject constructor(private val dao: CityDao) {
    operator fun invoke(): Flow<ResState<Map<String, CityWithRelationship>>> {
        return ResFlow {
            dao.get().collect { data -> emit(Success(data.associateBy { it.city.id })) }
        }
    }

    operator fun invoke(id: String): Flow<ResState<Pair<String, CityWithRelationship>>> {
        return ResFlow { dao.get(id).collect { emit(Success(it.city.id to it)) } }
    }
}
