package com.chrrissoft.room.reviews.db.entities

import com.chrrissoft.room.app.android.db.entities.RoomDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object ReviewDaoModule {
    @Provides
    fun provide(db: RoomDb) : ReviewDao = db.reviewDao
}
