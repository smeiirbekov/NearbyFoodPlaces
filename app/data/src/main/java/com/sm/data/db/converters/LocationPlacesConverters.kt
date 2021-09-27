package com.sm.data.db.converters

import androidx.room.TypeConverter
import com.sm.domain.base.JsonSerializer
import com.sm.domain.base.fromJson
import com.sm.domain.base.toJson
import com.sm.domain.models.LocationPlaces

object LocationPlacesConverters {

    lateinit var serializer: JsonSerializer

    fun inject(serializer: JsonSerializer) {
        this.serializer = serializer
    }

    @TypeConverter
    @JvmStatic
    fun jsonToItem(json: String): LocationPlaces? {
        return serializer.fromJson<LocationPlaces>(json)
    }

    @TypeConverter
    @JvmStatic
    fun itemToJson(item: LocationPlaces): String? {
        return serializer.toJson(item)
    }

}