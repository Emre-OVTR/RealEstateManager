package com.openclassrooms.realestatemanager.view

import androidx.lifecycle.*
import com.openclassrooms.realestatemanager.model.Estate
import com.openclassrooms.realestatemanager.model.Image
import com.openclassrooms.realestatemanager.repos.EstateRepository
import com.openclassrooms.realestatemanager.repos.ImageRepository
import kotlinx.coroutines.launch

class EstateViewModel(private val repository : EstateRepository, private val imageRepository: ImageRepository) : ViewModel() {


    val allEstates: LiveData<List<Estate.EstateEntity>> = repository.allEstates.asLiveData()
    fun getImages(estateId: Long): LiveData<List<Image>> = imageRepository.getImages(estateId).asLiveData()

    fun insert(estate: Estate.EstateEntity, uriList : List<String>) = viewModelScope.launch {
       // nous passons notre foction insert en tant que valeur de Id
        val id =  repository.insert(estate)
        for (uri in uriList){
            val image = Image(estateId = id, imageUri = uri)
            insertImage(image)
        }

    }

    fun insertImage(image: Image) = viewModelScope.launch {
        imageRepository.insert(image)
    }
}

class EstateViewModelFactory(private val repository: EstateRepository, private val imageRepository: ImageRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EstateViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return EstateViewModel(repository, imageRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}