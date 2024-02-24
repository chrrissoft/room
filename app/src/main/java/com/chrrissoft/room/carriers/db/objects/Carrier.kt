package com.chrrissoft.room.carriers.db.objects

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.ForeignKey.Companion.RESTRICT
import androidx.room.PrimaryKey
import com.chrrissoft.room.cities.db.objects.City
import com.chrrissoft.room.shared.db.PersonName

@Entity(tableName = "carriers", foreignKeys = [ForeignKey(
    entity = City::class,
    parentColumns = ["id"],
    childColumns = ["city_id"],
    onUpdate = CASCADE,
    onDelete = RESTRICT,
)])
data class Carrier(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "city_id") val cityId: String = "",
    @Embedded val name: PersonName = PersonName(),
) {
    companion object {
        val invalid = Carrier("")
    }
}
