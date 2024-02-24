package com.chrrissoft.room.app.android.db.di

import androidx.room.Room
import com.chrrissoft.room.RoomApp
import com.chrrissoft.room.app.android.db.entities.RoomDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomDbModule {
    @Provides
    @Singleton
    fun provide(app: RoomApp): RoomDb =
        Room.databaseBuilder(app, RoomDb::class.java, DB_NAME)
            .fallbackToDestructiveMigration()
            .build()

    private const val DB_NAME = "RoomDatabase_Chrrissoft"
}
