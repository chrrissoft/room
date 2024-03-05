package com.chrrissoft.room.cities.db.usecases

import com.chrrissoft.room.cities.db.objects.City
import com.chrrissoft.room.cities.db.entities.CityDao
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.app.ResState.Success
import com.chrrissoft.room.utils.FlowUtils.ResFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveCitiesUseCase @Inject constructor(private val dao: CityDao) {
    operator fun invoke(data: List<City>): Flow<ResState<Any>> {
        return ResFlow { emit(Success(dao.insert(data))) }
    }

    operator fun invoke(vararg data: City) : Flow<ResState<Any>> {
        return invoke(data.toList())
    }
}
