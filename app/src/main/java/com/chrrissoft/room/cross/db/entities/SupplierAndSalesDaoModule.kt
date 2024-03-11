package com.chrrissoft.room.cross.db.entities

import com.chrrissoft.room.app.android.db.entities.RoomDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object SupplierAndSalesDaoModule {
    @Provides
    fun provide(db: RoomDb): SupplierAndSalesDao = db.supplierAndSalesDao
}
