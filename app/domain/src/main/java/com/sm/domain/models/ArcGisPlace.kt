package com.sm.domain.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ArcGisPlace(
    @Json(name = "address")
    val address: String,
    @Json(name = "location")
    val location: ArcGisLocation,
    @Json(name = "attributes")
    val attributes: ArcGisAttributes?
)

@JsonClass(generateAdapter = true)
data class ArcGisLocation(
    @Json(name = "x")
    val x: Double,
    @Json(name = "y")
    val y: Double,
)

@JsonClass(generateAdapter = true)
data class ArcGisAttributes(
    @Json(name = "type")
    val type: String?,
    @Json(name = "city")
    val city: String?,
)
