package com.chrrissoft.room.cross.db.entities

import com.chrrissoft.room.app.android.db.entities.RoomDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object SellersAndProductsDaoModule {
    @Provides
    fun provide(db: RoomDb): SellersAndProductsDao = db.sellersAndProductsDao
}
