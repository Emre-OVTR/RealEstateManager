package com.openclassrooms.realestatemanager.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.openclassrooms.realestatemanager.model.Image
import kotlinx.coroutines.flow.Flow

@Dao
interface ImageDao {

    @Query("SELECT * FROM Image WHERE estateId = :estateId")
    fun getItems(estateId:Long): Flow<List<Image>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(image: Image)
}