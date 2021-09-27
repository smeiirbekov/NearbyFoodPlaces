package com.sm.nearbyfoodplaces.ui

import androidx.lifecycle.*
import com.sm.domain.base.Outcome
import com.sm.domain.models.LocPoint
import com.sm.domain.models.LocationPlaces
import com.sm.domain.usecases.GetAreaFoodPlacesUseCase
import com.sm.domain.usecases.GetCurrentLocationUseCase
import com.sm.nearbyfoodplaces.ui.MainActivity.Companion.CAMERA_UPDATE_DEBOUNCE_MILLIS
import com.sm.nearbyfoodplaces.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@FlowPreview
@ExperimentalCoroutinesApi
class MainViewModel @Inject constructor(
    private val getAreaFoodPlacesUseCase: GetAreaFoodPlacesUseCase,
    private val getCurrentLocationUseCase: GetCurrentLocationUseCase
): ViewModel() {

    private val _currentLocation = MutableLiveData<Event<Outcome<LocPoint>>>()
    val currentLocation: LiveData<Event<Outcome<LocPoint>>> = _currentLocation

    fun getCurrentLocation(){
        viewModelScope.launch {
            _currentLocation.value = Event(getCurrentLocationUseCase())
        }
    }

    private val _areaFoodPlaces = MutableSharedFlow<Pair<LocPoint, LocPoint>>()
    val areaFoodPlaces = _areaFoodPlaces.debounce(CAMERA_UPDATE_DEBOUNCE_MILLIS).mapLatest {
        getAreaFoodPlacesUseCase(it.first, it.second)
    }.asLiveData()

    fun getAreaFoodPlaces(
        lowerLeftCornerLat: Double, lowerLeftCornerLng: Double,
        upperRightCornerLat: Double, upperRightCornerLng: Double
    ){
        viewModelScope.launch {
            _areaFoodPlaces.emit(LocPoint(lowerLeftCornerLat, lowerLeftCornerLng)
                    to LocPoint(upperRightCornerLat, upperRightCornerLng))
        }
    }
}