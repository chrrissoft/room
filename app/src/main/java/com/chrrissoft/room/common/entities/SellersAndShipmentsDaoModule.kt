package com.chrrissoft.room.common.entities

import com.chrrissoft.room.app.android.db.entities.RoomDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object SellersAndShipmentsDaoModule {
    @Provides
    fun provide(db: RoomDb): SellersAndShipmentsDao = db.sellersAndShipmentsDao
}
