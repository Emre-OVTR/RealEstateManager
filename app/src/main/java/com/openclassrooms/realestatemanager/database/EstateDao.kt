package com.openclassrooms.realestatemanager.database

import androidx.room.*
import com.openclassrooms.realestatemanager.model.Estate
import com.openclassrooms.realestatemanager.model.FullEstate
import kotlinx.coroutines.flow.Flow

@Dao
interface EstateDao {


    @Query("SELECT * FROM estate_table")
    fun getEstates(): Flow<List<FullEstate>>

    @Query("SELECT * FROM estate_table WHERE id = :id ")
    fun getEstatesById(id : Long): Flow<Estate>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(estate: Estate) : Long

    @Update
    suspend fun update(estate: Estate)


}