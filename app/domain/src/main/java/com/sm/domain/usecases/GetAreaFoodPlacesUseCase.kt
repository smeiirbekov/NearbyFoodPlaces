package com.sm.domain.usecases

import com.sm.domain.base.Outcome
import com.sm.domain.base.map
import com.sm.domain.models.ArcGisPlace
import com.sm.domain.repositories.PlacesRepository
import com.sm.domain.models.LocPoint
import javax.inject.Inject

const val ARC_GIS_CATEGORY_FOOD = "food"
const val DEFAULT_PLACES_LIMIT = 20

class GetAreaFoodPlacesUseCase @Inject constructor(
    private val placesRepository: PlacesRepository
) {

    suspend operator fun invoke(
        lowerLeftCorner: LocPoint,
        upperRightCorner: LocPoint,
        limit: Int = DEFAULT_PLACES_LIMIT
    ): Outcome<List<ArcGisPlace>> {
        return placesRepository.getLocationPlaces(
            lowerLeftCorner,
            upperRightCorner,
            ARC_GIS_CATEGORY_FOOD,
            limit
        ).map {
            it?.candidates
        }
    }

}