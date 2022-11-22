package com.openclassrooms.realestatemanager.repos

import androidx.annotation.WorkerThread
import com.openclassrooms.realestatemanager.database.EstateDao
import com.openclassrooms.realestatemanager.model.Estate
import kotlinx.coroutines.flow.Flow
import java.util.*


class EstateRepository(private val estateDao: EstateDao) {

    val allEstates: Flow<List<Estate.EstateEntity>> = estateDao.getEstates()


    @WorkerThread
    suspend fun insert(estate: Estate.EstateEntity) : Long {
       return estateDao.insert(estate)
    }


}