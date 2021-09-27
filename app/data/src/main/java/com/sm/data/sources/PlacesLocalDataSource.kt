package com.sm.data.sources

import com.sm.domain.models.LocPoint
import com.sm.domain.models.LocationPlaces
import com.sm.domain.sources.PlacesDataSource

class PlacesLocalDataSource: PlacesDataSource {

    private var lastQuery: Pair<String, LocationPlaces>? = null

    private fun generateQueryId(
        lowerLeftCorner: LocPoint,
        upperRightCorner: LocPoint,
        category: String,
        limit: Int
    ) = "$lowerLeftCorner,$upperRightCorner,$category,$limit"

    override suspend fun getLocationPlaces(
        lowerLeftCorner: LocPoint,
        upperRightCorner: LocPoint,
        category: String,
        limit: Int
    ): LocationPlaces? {
        val id = generateQueryId(lowerLeftCorner, upperRightCorner, category, limit)
        return if (lastQuery?.first == id) {
            lastQuery?.second
        } else {
            null
        }
    }

    override suspend fun saveLastQuery(
        lowerLeftCorner: LocPoint,
        upperRightCorner: LocPoint,
        category: String,
        limit: Int,
        response: LocationPlaces
    ) {
        val id = generateQueryId(lowerLeftCorner, upperRightCorner, category, limit)
        lastQuery = id to response
    }

}