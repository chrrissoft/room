package com.chrrissoft.room.room

import android.content.Context
import androidx.room.*
import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [],
    version = 2,
    autoMigrations = [
        AutoMigration(from = 1, to = 2, spec = MyDatabase.AutoMigrationVersion2::class)
    ]
)
abstract class MyDatabase : RoomDatabase() {
    @RenameTable(fromTableName = "User", toTableName = "AppUser")
    @DeleteColumn(tableName = "User", columnName = "last_name")
    class AutoMigrationVersion2 : AutoMigrationSpec {
        override fun onPostMigrate(db: SupportSQLiteDatabase) {
            super.onPostMigrate(db)
        }
    }
}


/******************************** manual migrations ********************************/

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("CREATE TABLE `Fruit` (`id` INTEGER, `name` TEXT, " +
                "PRIMARY KEY(`id`))")
    }
}

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE Book ADD COLUMN pub_year INTEGER")
        database.execSQL("ALTER TABLE User ADD COLUMN last_name varchar")
    }
}


fun createDatabase(ctx: Context): RoomDatabase {
    return Room.databaseBuilder(ctx, MyDatabase::class.java, "")
        .addMigrations(MIGRATION_1_2, MIGRATION_2_3).build()
}
