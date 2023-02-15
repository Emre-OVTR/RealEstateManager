package com.openclassrooms.realestatemanager.repos

import androidx.annotation.WorkerThread
import com.openclassrooms.realestatemanager.database.ImageDao
import com.openclassrooms.realestatemanager.model.Image
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ImageRepository @Inject constructor(private val imageDao: ImageDao) {

    fun getImages(estateId:Long): Flow<List<Image>> {
        return imageDao.getItems(estateId)
    }
    @WorkerThread
    suspend fun insert(image: Image) : Long{
       return imageDao.insert(image)
    }

    suspend fun delete(image: Image){
        return imageDao.delete(image)
    }

}