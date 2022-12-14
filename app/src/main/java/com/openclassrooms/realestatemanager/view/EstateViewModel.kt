package com.openclassrooms.realestatemanager.view

import androidx.core.net.toUri
import androidx.lifecycle.*
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

    fun insert(estate: Estate, uriList : List<String>) = viewModelScope.launch {
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


