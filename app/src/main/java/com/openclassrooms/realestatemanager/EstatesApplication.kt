package com.openclassrooms.realestatemanager

import android.app.Application
import com.openclassrooms.realestatemanager.database.EstateRoomDatabase
import com.openclassrooms.realestatemanager.repos.EstateRepository
import com.openclassrooms.realestatemanager.repos.ImageRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class EstatesApplication : Application() {


    private val applicationScope = CoroutineScope(SupervisorJob())

    private val database by lazy { EstateRoomDatabase.getDatabase(this, applicationScope)}
    val repository by lazy { EstateRepository(database.estateDao()) }
    val imageRepository by lazy { ImageRepository(database.imageDao()) }


}