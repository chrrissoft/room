package com.chrrissoft.room.payments.db.entities

import com.chrrissoft.room.app.android.db.entities.RoomDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object PaymentDaoModule {
    @Provides
    fun provide(db: RoomDb) : PaymentDao = db.paymentDao
}
