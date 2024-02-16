package com.chrrissoft.room.countries.db.entities

import com.chrrissoft.room.app.android.db.entities.RoomDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object CountryDaoModule {
    @Provides
    fun provide(db: RoomDb) : CountryDao = db.countryDao
}
