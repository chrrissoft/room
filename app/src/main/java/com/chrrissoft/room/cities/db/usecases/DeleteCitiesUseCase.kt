package com.chrrissoft.room.cities.db.usecases

import com.chrrissoft.room.cities.db.objects.City
import com.chrrissoft.room.cities.db.entities.CityDao
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.utils.FlowUtils.ResFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteCitiesUseCase @Inject constructor(private val dao: CityDao) {
    operator fun invoke(data: List<City>): Flow<ResState<Any>> {
        return ResFlow { dao.delete(data) }
    }

    operator fun invoke(vararg data: City) : Flow<ResState<Any>> {
        return invoke(data.toList())
    }
}
