package com.chrrissoft.room.room

import android.content.Context
import androidx.room.*
import java.util.*

class Converters(
    private val dependency: Dependency
) {
    @TypeConverter
    fun stringToExample(string: String): Example {
        return Example(dependency)
    }

    @TypeConverter
    fun exampleToString(example: Example): String {
        return example.toString()
    }
}

class Example(private val dependency: Dependency) {
    fun doAnything() {}
}

interface Dependency {
    class DependencyImpl: Dependency
}

@Database(entities = [User::class], version = 1)
@TypeConverters(Converters::class)
abstract class ChrisDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}

fun createDB(ctx: Context): RoomDatabase {
    return Room.databaseBuilder(ctx, MyDatabase::class.java, "")
        .addTypeConverter(Converters(Dependency.DependencyImpl()))
        .build()
}
