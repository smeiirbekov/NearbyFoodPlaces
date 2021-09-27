package com.sm.data.repositories

import android.content.Context
import com.sm.data.managers.SingleLocationManager
import com.sm.domain.base.Outcome
import com.sm.domain.models.LocPoint
import com.sm.domain.repositories.LocationRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withTimeout

@ExperimentalCoroutinesApi
class LocationRepositoryImpl(
    private val context: Context
): LocationRepository {

    companion object {
        private const val LOCATION_REQUEST_TIMEOUT_MILLIS = 30 * 1000L // 30 seconds
    }

    override suspend fun getCurrentLocation(): Outcome<LocPoint> {
        val singleLocationManager = SingleLocationManager(context)
        return try {
            withTimeout(LOCATION_REQUEST_TIMEOUT_MILLIS) {
                suspendCancellableCoroutine { cont ->
                    singleLocationManager.onLocationResult = {
                        cont.resume(Outcome.Success(it), null)
                    }
                    if (!singleLocationManager.requestSingleUpdate()) {
                        cont.resume(Outcome.Error.ResponseError(), null)
                    }
                }
            }
        } catch (e: TimeoutCancellationException) {
            Outcome.Error.ConnectionError
        } finally {
            singleLocationManager.clear()
        }
    }

}