package com.chrrissoft.room.promotions.db.entities

import com.chrrissoft.room.app.android.db.entities.RoomDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object PromotionDaoModule {
    @Provides
    fun provide(db: RoomDb) : PromotionDao = db.promotionDao
}
