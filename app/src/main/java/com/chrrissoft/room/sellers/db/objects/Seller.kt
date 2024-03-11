package com.chrrissoft.room.sellers.db.objects

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.chrrissoft.room.cities.db.objects.City
import com.chrrissoft.room.shared.db.PersonName

@Entity(tableName = "sellers", foreignKeys = [ForeignKey(
    entity = City::class,
    parentColumns = ["id"],
    childColumns = ["city_id"],
    // onUpdate = ForeignKey.CASCADE,
    // onDelete =  ForeignKey.RESTRICT,
)])
data class Seller(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: String,
    @Embedded val name: PersonName = PersonName(),
    @ColumnInfo(name = "city_id") val cityId: String = "",
) {
    companion object {
        val invalid = Seller("")
    }
}
