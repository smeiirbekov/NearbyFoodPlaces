package com.sm.nearbyfoodplaces.di

import android.content.Context
import com.sm.api.ArcGISPlacesInterceptor
import com.sm.api.PLACES_BASE_URL
import com.sm.api.PlacesApi
import com.sm.api.base.ApiBuilder
import com.sm.data.base.MoshiSerializer
import com.sm.data.repositories.LocationRepositoryImpl
import com.sm.data.repositories.PlacesRepositoryImpl
import com.sm.data.sources.PlacesLocalDataSource
import com.sm.domain.repositories.PlacesRepository
import com.sm.domain.base.JsonSerializer
import com.sm.domain.repositories.LocationRepository
import com.sm.domain.sources.PlacesDataSource
import com.sm.nearbyfoodplaces.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideContext(@ApplicationContext context: Context) = context

    @Singleton
    @Provides
    fun provideJsonSerializer(): JsonSerializer = MoshiSerializer(
        Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
    )

    @Singleton
    @Provides
    fun providePlacesApiService(
        serializer: JsonSerializer
    ): PlacesApi = ApiBuilder(serializer).buildService(
        PLACES_BASE_URL,
        ArcGISPlacesInterceptor(BuildConfig.ARCGIS_API_KEY)
    )

    @Singleton
    @Provides
    fun providePlacesLocalDataSource(): PlacesDataSource = PlacesLocalDataSource()

    @Singleton
    @Provides
    fun providePlacesRepository(
        service: PlacesApi,
        localDataSource: PlacesDataSource
    ): PlacesRepository = PlacesRepositoryImpl(service, localDataSource)

    @Singleton
    @Provides
    @ExperimentalCoroutinesApi
    fun provideLocationRepository(
        @ApplicationContext context: Context
    ): LocationRepository = LocationRepositoryImpl(context)

}