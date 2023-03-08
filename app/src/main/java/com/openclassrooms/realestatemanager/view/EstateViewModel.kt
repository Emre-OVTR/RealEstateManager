package com.openclassrooms.realestatemanager.view

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.sqlite.db.SimpleSQLiteQuery
import com.openclassrooms.realestatemanager.model.Estate
import com.openclassrooms.realestatemanager.model.FullEstate
import com.openclassrooms.realestatemanager.model.Image
import com.openclassrooms.realestatemanager.repos.EstateRepository
import com.openclassrooms.realestatemanager.repos.ImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EstateViewModel @Inject constructor(private val repository : EstateRepository, private val imageRepository: ImageRepository) : ViewModel() {


    val allEstates: LiveData<List<FullEstate>> = repository.allEstates.asLiveData()
    fun getImages(estateId: Long): LiveData<List<Image>> = imageRepository.getImages(estateId).asLiveData()

    fun insert(estate: Estate, uriList : List<Image>) = viewModelScope.launch {
       // nous passons notre foction insert en tant que valeur de Id
       val id = repository.insert(estate)
        for (uri in uriList){
            val image = Image(imageUri = uri.imageUri, estateId = id)
            insertImage(image)
        }
    }

    private fun insertImage(image: Image) = viewModelScope.launch {
        imageRepository.insert(image)
    }
    private fun deleteImage(image: Image) = viewModelScope.launch {
        imageRepository.delete(image)
    }

    fun getEstateById(id : Long): LiveData<Estate> = repository.getEstateById(id).asLiveData()

    fun updateEstate(estate: Estate,imageToDelete: List<Image>, imageToInsert: List<Image> ) = viewModelScope.launch {

        repository.update(estate)
        for (image in imageToDelete){
            deleteImage(image)
        }
        for (image in imageToInsert){
          //  val imageToAdd = Image(imageUri = image.imageUri, estateId = estate.id)
            insertImage(image)
        }

    }

    fun getFilteredEstates(queryToConvert: String, args: Array<Any>) : LiveData<List<FullEstate>> {
        val query = SimpleSQLiteQuery(queryToConvert, args)
        Log.e("GET_ESTATES_BY_SEARCH","Query to execute : ${query.sql}")
        args.forEach{
            Log.e("GET_ESTATES_BY_SEARCH", "Args : $it")
        }
        return repository.getFilteredEstates(query).asLiveData()
    }


}


