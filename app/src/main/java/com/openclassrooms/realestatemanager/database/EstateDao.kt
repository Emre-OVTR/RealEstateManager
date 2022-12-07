package com.openclassrooms.realestatemanager.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.openclassrooms.realestatemanager.model.Estate
import com.openclassrooms.realestatemanager.model.FullEstate
import kotlinx.coroutines.flow.Flow

@Dao
interface EstateDao {


    @Query("SELECT * FROM estate_table")
    fun getEstates(): Flow<List<FullEstate>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(estate: Estate) : Long


}