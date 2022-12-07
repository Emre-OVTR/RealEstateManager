package com.openclassrooms.realestatemanager.repos

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.openclassrooms.realestatemanager.database.ImageDao
import com.openclassrooms.realestatemanager.model.Image
import kotlinx.coroutines.flow.Flow

class ImageRepository(private val imageDao: ImageDao) {

    fun getImages(estateId:Long): Flow<List<Image>> {
        return imageDao.getItems(estateId)
    }
    @WorkerThread
    suspend fun insert(image: Image) : Long{
       return imageDao.insert(image)
    }

}