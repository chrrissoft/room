package com.chrrissoft.room.costumers.db.objects

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.chrrissoft.room.cities.db.objects.City
import com.chrrissoft.room.shared.db.PersonName

@Entity(tableName = "costumers", foreignKeys = [ForeignKey(
    entity = City::class,
    parentColumns = ["id"],
    childColumns = ["city_id"],
    // onUpdate = ForeignKey.CASCADE,
    // onDelete =  ForeignKey.RESTRICT,
)])
data class Costumer(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: String,
    @Embedded val name: PersonName = PersonName(),
    @ColumnInfo(name = "city_id") val cityId: String? = null,
) {
    companion object {
        val invalid = Costumer("")
    }
}
