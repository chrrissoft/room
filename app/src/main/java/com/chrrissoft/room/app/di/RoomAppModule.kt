package com.chrrissoft.room.app.di

import android.content.Context
import com.chrrissoft.room.RoomApp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RoomAppModule {
    @Provides
    fun provide(@ApplicationContext ctx: Context): RoomApp = ctx as RoomApp
}
