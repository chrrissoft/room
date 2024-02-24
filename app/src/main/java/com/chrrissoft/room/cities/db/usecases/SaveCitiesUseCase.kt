package com.chrrissoft.room.cities.db.usecases

import com.chrrissoft.room.cities.db.objects.CityWithRelationship
import com.chrrissoft.room.cities.db.entities.CityDao
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.app.ResState.Success
import com.chrrissoft.room.utils.FlowUtils.ResFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveCitiesUseCase @Inject constructor(private val dao: CityDao) {
    operator fun invoke(data: List<CityWithRelationship>): Flow<ResState<Any>> {
        return ResFlow {
            dao.insert(data.map { it.city.copy(countryId = it.country.id) })
            emit(Success(data = 0))
        }
    }

    operator fun invoke(vararg data: CityWithRelationship) : Flow<ResState<Any>> {
        return invoke(data.toList())
    }
}
