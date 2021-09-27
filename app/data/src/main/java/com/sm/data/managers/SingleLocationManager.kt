package com.sm.data.managers

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.sm.domain.models.LocPoint

class SingleLocationManager(
    private val context: Context
) {

    companion object {
        private const val LAST_LOCATION_LIFESPAN_MILLIS = 1 * 60 * 1000L // 1 minute
    }

    var onLocationResult: ((LocPoint) -> Unit)? = null

    private val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    private val locationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            clear()
            onLocationResult?.invoke(LocPoint(location.latitude, location.longitude))
        }
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
    }

    fun clear(){
        locationManager.removeUpdates(locationListener)
    }

    /**
     * returns false if no permission granted
     */
    fun requestSingleUpdate(): Boolean {
        return if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            false
        } else {
            val lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            if (lastLocation != null && lastLocation.time > System.currentTimeMillis() - LAST_LOCATION_LIFESPAN_MILLIS) {
                onLocationResult?.invoke(LocPoint(lastLocation.latitude, lastLocation.longitude))
            } else {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, locationListener)
            }
            true
        }
    }
}