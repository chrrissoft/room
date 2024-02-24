package com.chrrissoft.room.cities.db.objects

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.chrrissoft.room.countries.db.objects.Country

@Entity(
    tableName = "cities", foreignKeys = [ForeignKey(
        entity = Country::class,
        parentColumns = ["id"],
        childColumns = ["country_id"],
        onUpdate = ForeignKey.CASCADE,
        onDelete = ForeignKey.RESTRICT,
    )])
data class City(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "name") val name: String = "",
    @ColumnInfo(name = "country_id") val countryId: String = "",
) {
    companion object {
        val invalid = City("")
    }
}
