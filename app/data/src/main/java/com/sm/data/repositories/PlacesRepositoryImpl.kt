package com.sm.data.repositories

import com.sm.api.PlacesApi
import com.sm.domain.repositories.PlacesRepository
import com.sm.domain.base.Outcome
import com.sm.domain.base.doOnSuccess
import com.sm.domain.models.LocPoint
import com.sm.domain.sources.PlacesDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PlacesRepositoryImpl(
    private val service: PlacesApi,
    private val localDataSource: PlacesDataSource
): PlacesRepository {

    override suspend fun getLocationPlaces(
        lowerLeftCorner: LocPoint,
        upperRightCorner: LocPoint,
        category: String,
        limit: Int
    ) = withContext(Dispatchers.IO) {
        localDataSource.getLocationPlaces(
            lowerLeftCorner,
            upperRightCorner,
            category,
            limit
        )?.let {
            Outcome.Success(it)
        } ?: service.getLocationPlaces(
            category,
            "${lowerLeftCorner.lat},${lowerLeftCorner.lng},${upperRightCorner.lat},${upperRightCorner.lng}",
            limit
        ).doOnSuccess { data ->
            data?.let {
                localDataSource.saveLastQuery(lowerLeftCorner, upperRightCorner, category, limit, it)
            }
        }
    }

}