package com.sm.domain.usecases

import com.sm.domain.repositories.LocationRepository
import javax.inject.Inject

class GetCurrentLocationUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {

    suspend operator fun invoke() = locationRepository.getCurrentLocation()

}