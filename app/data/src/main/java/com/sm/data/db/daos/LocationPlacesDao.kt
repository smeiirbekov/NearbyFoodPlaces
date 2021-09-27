package com.sm.data.db.daos

import androidx.room.*
import com.sm.data.db.entities.LocationPlacesItem

@Dao
interface LocationPlacesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: LocationPlacesItem)

    @Query("SELECT * FROM LocationPlacesItem WHERE id == :id")
    suspend fun getItem(id: String): LocationPlacesItem?

    @Query("DELETE FROM LocationPlacesItem")
    suspend fun deleteAllItems()

}