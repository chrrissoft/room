package com.chrrissoft.room.countries.db.objects

import androidx.room.Embedded
import androidx.room.Relation
import com.chrrissoft.room.cities.db.objects.City
import com.chrrissoft.room.cities.db.objects.CityWithRelationship

data class CountryNestedWithRelationship(
    @Embedded val country: Country,
    @Relation(entity = City::class, parentColumn = "id", entityColumn = "country_id")
    val cities: List<CityWithRelationship> = emptyList(),
)
