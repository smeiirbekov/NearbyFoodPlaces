package com.sm.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sm.domain.models.LocationPlaces

@Entity(tableName = "LocationPlacesItem")
data class LocationPlacesItem(
    @PrimaryKey
    val id: String,
    val value: LocationPlaces?
)