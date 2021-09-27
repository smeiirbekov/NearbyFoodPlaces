package com.sm.data.db

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sm.data.db.converters.LocationPlacesConverters
import com.sm.data.db.daos.LocationPlacesDao
import com.sm.data.db.entities.LocationPlacesItem

@androidx.room.Database(
    entities = [LocationPlacesItem::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(LocationPlacesConverters::class)
abstract class Database : RoomDatabase() {
    companion object {
        private const val NEARBY_FOODS_DB = "nearby_foods.db"
        fun create(context: Context): Database {
            return Room.databaseBuilder(context, Database::class.java, NEARBY_FOODS_DB)
                .fallbackToDestructiveMigration()
                .build()
        }
    }

    abstract fun locationPlaces(): LocationPlacesDao
}