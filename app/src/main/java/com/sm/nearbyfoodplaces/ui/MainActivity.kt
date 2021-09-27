package com.sm.nearbyfoodplaces.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterManager
import com.sm.domain.base.onConnectionError
import com.sm.domain.base.onResponseError
import com.sm.domain.base.onSuccess
import com.sm.domain.base.onUnknownError
import com.sm.nearbyfoodplaces.R
import com.sm.nearbyfoodplaces.databinding.MainActivityBinding
import com.sm.nearbyfoodplaces.ui.adapters.MyClusterItem
import com.sm.nearbyfoodplaces.ui.adapters.MyClusterRenderer
import com.sm.nearbyfoodplaces.ui.adapters.PlacesAdapter
import com.sm.nearbyfoodplaces.utils.EventObserver
import com.sm.nearbyfoodplaces.utils.bindings.BindingActivity
import com.sm.nearbyfoodplaces.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import timber.log.Timber

@AndroidEntryPoint
@FlowPreview
@ExperimentalCoroutinesApi
class MainActivity : BindingActivity<MainActivityBinding>(MainActivityBinding::inflate) {

    companion object {
        private const val CODE_PERMISSION_LOCATION = 100
        private const val CAMERA_UPDATE_DURATION_MILLIS = 300
        private const val CAMERA_ZOOM_LEVEL = 14f
        const val CAMERA_UPDATE_DEBOUNCE_MILLIS = 300L
    }

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val placesAdapter = PlacesAdapter {
            showToast(it.address)
        }
        binding.rvPlaces.adapter = placesAdapter
        binding.rvPlaces.layoutManager = LinearLayoutManager(
            this,
            RecyclerView.HORIZONTAL,
            false
        )
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync { map ->
            viewModel.getCurrentLocation()
            viewModel.currentLocation.observe(this, EventObserver {
                it.onSuccess { data ->
                    if (data == null) return@onSuccess
                    val cameraUpdate = CameraUpdateFactory.newLatLngZoom(
                        LatLng(data.lat, data.lng), CAMERA_ZOOM_LEVEL
                    )
                    map.animateCamera(cameraUpdate, CAMERA_UPDATE_DURATION_MILLIS, null)
                }.onResponseError { _, _ ->
                    requestLocationPermission()
                }.onConnectionError {
                    showToast(R.string.error_connection)
                }
            })
            map.setOnCameraMoveListener {
                viewModel.getAreaFoodPlaces(
                    map.projection.visibleRegion.latLngBounds.southwest.latitude,
                    map.projection.visibleRegion.latLngBounds.southwest.longitude,
                    map.projection.visibleRegion.latLngBounds.northeast.latitude,
                    map.projection.visibleRegion.latLngBounds.northeast.longitude
                )
            }
            val clusterManager = ClusterManager<MyClusterItem>(this, map)
            clusterManager.renderer = MyClusterRenderer(this, map, clusterManager)
            map.setOnCameraIdleListener(clusterManager)
            clusterManager.setOnClusterItemClickListener {
                showToast(it.myTitle)
                false
            }
            val markers = mutableSetOf<MyClusterItem>() /* Prevent duplicate markers on map */
            viewModel.areaFoodPlaces.observe(this) {
                Timber.d("Got: %s", it)
                it.onSuccess { list ->
                    val newItems = list?.mapNotNull { place ->
                        val clusterItem = MyClusterItem(
                            LatLng(place.location.y, place.location.x),
                            place.address,
                            place.attributes?.type
                        )
                        if (!markers.contains(clusterItem)) {
                            markers.add(clusterItem)
                            clusterItem
                        } else null
                    } ?: listOf()
                    placesAdapter.submitList(list)
                    clusterManager.addItems(newItems)
                    clusterManager.cluster()
                }.onResponseError { error, _ ->
                    showToast(error.message)
                }.onConnectionError {
                    showToast(R.string.error_connection)
                }.onUnknownError {
                    showToast(R.string.error_unknown)
                }
            }
        }

    }

    private fun requestLocationPermission(){
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            CODE_PERMISSION_LOCATION
        )
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            CODE_PERMISSION_LOCATION -> {
                if (grantResults.firstOrNull() == PackageManager.PERMISSION_GRANTED) {
                    viewModel.getCurrentLocation()
                }
            }
        }
    }

}