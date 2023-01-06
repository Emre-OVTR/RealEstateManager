package com.openclassrooms.realestatemanager.repos

import androidx.annotation.WorkerThread
import com.openclassrooms.realestatemanager.database.EstateDao
import com.openclassrooms.realestatemanager.model.Estate
import com.openclassrooms.realestatemanager.model.FullEstate
import kotlinx.coroutines.flow.Flow
import java.util.*
import javax.inject.Inject


class EstateRepository @Inject constructor(private val estateDao: EstateDao) {

    val allEstates: Flow<List<FullEstate>> = estateDao.getEstates()


    @WorkerThread
    suspend fun insert(estate: Estate) : Long {
       return estateDao.insert(estate)
    }


}