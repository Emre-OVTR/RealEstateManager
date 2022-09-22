package com.openclassrooms.realestatemanager

import android.app.Application
import com.openclassrooms.realestatemanager.database.EstateRoomDatabase
import com.openclassrooms.realestatemanager.repos.EstateRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class EstatesApplication : Application() {


    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { EstateRoomDatabase.getDatabase(this, applicationScope)}
    val repository by lazy { EstateRepository(database.estateDao()) }


}