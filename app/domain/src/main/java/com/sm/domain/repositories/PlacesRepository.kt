package com.sm.domain.repositories

import com.sm.domain.base.Outcome
import com.sm.domain.models.LocPoint
import com.sm.domain.models.LocationPlaces

interface PlacesRepository {

    suspend fun getLocationPlaces(
        lowerLeftCorner: LocPoint,
        upperRightCorner: LocPoint,
        category: String,
        limit: Int
    ): Outcome<LocationPlaces>

}