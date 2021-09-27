package com.sm.domain.repositories

import com.sm.domain.base.Outcome
import com.sm.domain.models.LocPoint

interface LocationRepository {

    suspend fun getCurrentLocation(): Outcome<LocPoint>

}