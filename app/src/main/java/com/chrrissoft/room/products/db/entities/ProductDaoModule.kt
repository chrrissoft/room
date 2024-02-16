package com.chrrissoft.room.products.db.entities

import com.chrrissoft.room.app.android.db.entities.RoomDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object ProductDaoModule {
    @Provides
    fun provide(db: RoomDb) : ProductDao = db.productDao
}
