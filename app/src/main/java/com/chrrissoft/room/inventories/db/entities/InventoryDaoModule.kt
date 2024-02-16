package com.chrrissoft.room.inventories.db.entities

import com.chrrissoft.room.app.android.db.entities.RoomDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object InventoryDaoModule {
    @Provides
    fun provide(db: RoomDb) : InventoryDao = db.inventoryDao
}
