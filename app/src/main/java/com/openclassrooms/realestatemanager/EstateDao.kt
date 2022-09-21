package com.openclassrooms.realestatemanager

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface EstateDao {


    @Query("SELECT * FROM estate_table")
    fun getEstates(): Flow<List<Estate>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(estate: Estate)

}