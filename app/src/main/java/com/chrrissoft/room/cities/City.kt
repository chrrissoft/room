package com.chrrissoft.room.cities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.chrrissoft.room.countries.Country

@Entity(
    tableName = "cities", foreignKeys = [ForeignKey(
        entity = Country::class,
        parentColumns = ["id"],
        childColumns = ["country_id"]
    )])
data class City(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id") val id: Long = 0L,
    @ColumnInfo("name") val name: String,
    @ColumnInfo("country_id") val countryId: Long,
)
