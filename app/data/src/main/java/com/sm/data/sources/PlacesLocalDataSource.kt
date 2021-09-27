package com.sm.data.sources

import com.sm.data.db.daos.LocationPlacesDao
import com.sm.data.db.entities.LocationPlacesItem
import com.sm.domain.models.LocPoint
import com.sm.domain.models.LocationPlaces
import com.sm.domain.sources.PlacesDataSource

class PlacesLocalDataSource(
    private val locationPlacesDao: LocationPlacesDao
): PlacesDataSource {

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
        return locationPlacesDao.getItem(id)?.value
    }

    override suspend fun saveLastQuery(
        lowerLeftCorner: LocPoint,
        upperRightCorner: LocPoint,
        category: String,
        limit: Int,
        response: LocationPlaces
    ) {
        val id = generateQueryId(lowerLeftCorner, upperRightCorner, category, limit)
        locationPlacesDao.deleteAllItems()
        val item = LocationPlacesItem(id, response)
        locationPlacesDao.insertItem(item)
    }

}