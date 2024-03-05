package com.chrrissoft.room.countries.db.usecases

import com.chrrissoft.room.countries.db.objects.Country
import com.chrrissoft.room.countries.db.entities.CountryDao
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.app.ResState.Success
import com.chrrissoft.room.utils.FlowUtils.ResFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveCountriesUseCase @Inject constructor(private val dao: CountryDao) {
    operator fun invoke(data: List<Country>): Flow<ResState<Any>> {
        return ResFlow { emit(Success(dao.insert(data))) }
    }

    operator fun invoke(vararg data: Country) : Flow<ResState<Any>> {
        return invoke(data.toList())
    }
}
