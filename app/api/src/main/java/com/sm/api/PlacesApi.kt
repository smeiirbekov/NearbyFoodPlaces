package com.sm.api

import com.sm.domain.base.Outcome
import com.sm.domain.models.LocationPlaces
import retrofit2.http.GET
import retrofit2.http.Query

const val PLACES_BASE_URL = "https://geocode-api.arcgis.com/arcgis/rest/services/World/GeocodeServer/"

@JvmSuppressWildcards
interface PlacesApi {

    @GET("findAddressCandidates")
    suspend fun getLocationPlaces(
        @Query("category") category: String,
        @Query("searchExtent") searchExtent: String,
        @Query("maxLocations") limit: Int
    ): Outcome<LocationPlaces>

}