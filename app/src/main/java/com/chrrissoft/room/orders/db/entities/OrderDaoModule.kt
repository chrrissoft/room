package com.chrrissoft.room.orders.db.entities

import com.chrrissoft.room.app.android.db.entities.RoomDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object OrderDaoModule {
    @Provides
    fun provide(db: RoomDb) : OrderDao = db.orderDao
}
