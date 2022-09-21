package com.openclassrooms.realestatemanager

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow


class EstateRepository(private val estateDao: EstateDao) {

    val allEstates: Flow<List<Estate>> = estateDao.getEstates()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(estate: Estate) {
        estateDao.insert(estate)
    }
}