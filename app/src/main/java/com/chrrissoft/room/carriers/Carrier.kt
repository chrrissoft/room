package com.chrrissoft.room.carriers

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey
import com.chrrissoft.room.cities.City

@Entity(tableName = "carries", foreignKeys = [ForeignKey(
    entity = City::class,
    parentColumns = ["id"],
    childColumns = ["city_id"],
    onUpdate = CASCADE,
    deferred = true
)])
data class Carrier(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Long = 0L,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "last_name") val lastName: String,
    @ColumnInfo(name = "complete_name") val completeName: String,
    @ColumnInfo(name = "city_id") val cityId: Long,
)
