package com.chrrissoft.room.countries.db.objects

import androidx.room.Embedded
import androidx.room.Relation
import com.chrrissoft.room.cities.db.objects.City

data class CountryWithRelationship(
    @Embedded val country: Country,
    @Relation(parentColumn = "id", entityColumn = "country_id")
    val cities: List<City>,
)
