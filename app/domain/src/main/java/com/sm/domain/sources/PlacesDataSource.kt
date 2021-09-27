package com.sm.domain.sources

import com.sm.domain.models.LocPoint
import com.sm.domain.models.LocationPlaces

interface PlacesDataSource{

    suspend fun getLocationPlaces(
        lowerLeftCorner: LocPoint,
        upperRightCorner: LocPoint,
        category: String,
        limit: Int
    ): LocationPlaces?

    suspend fun saveLastQuery(
        lowerLeftCorner: LocPoint,
        upperRightCorner: LocPoint,
        category: String,
        limit: Int,
        response: LocationPlaces
    )
}