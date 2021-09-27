package com.sm.domain.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LocationPlaces(
    @Json(name = "candidates")
    val candidates: List<ArcGisPlace>
)

