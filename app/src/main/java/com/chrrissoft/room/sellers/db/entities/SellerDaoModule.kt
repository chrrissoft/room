package com.chrrissoft.room.sellers.db.entities

import com.chrrissoft.room.app.android.db.entities.RoomDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object SellerDaoModule {
    @Provides
    fun provide(db: RoomDb) : SellerDao = db.sellerDao
}
