package com.openclassrooms.realestatemanager.view

import androidx.core.net.toUri
import androidx.lifecycle.*
import com.google.android.gms.maps.model.LatLng
import com.openclassrooms.realestatemanager.database.EstateDao
import com.openclassrooms.realestatemanager.model.Estate
import com.openclassrooms.realestatemanager.model.FullEstate
import com.openclassrooms.realestatemanager.model.Image
import com.openclassrooms.realestatemanager.repos.EstateRepository
import com.openclassrooms.realestatemanager.repos.ImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
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

    fun getEstateById(id : Long): LiveData<Estate> = repository.getEstateById(id).asLiveData()


    fun updateEstate(estate: Estate) = viewModelScope.launch {
        repository.update(estate)
    }




    fun updateEstate(  id : Long,
                       price: Int,
                       description : String,
                       isNearParks : Boolean,
                       isNearShops : Boolean,
                       isNearSchools: Boolean,
                       isNearHighway: Boolean,
                       estateTypePosition: Int,
                       estateTypeName: String,
                       surface: Int,
                       roomNumber: Int,
                       bathroomNumber: Int,
                       bedRoomNumber: Int,
                       address: String,
                       longitude : Double,
                       latitude : Double,
                       creationDate : LocalDate) {
        val updatedEstate = getUpdatedEstateEntry(id, price, description, isNearParks, isNearShops, isNearSchools, isNearHighway, estateTypePosition, estateTypeName, surface, roomNumber, bathroomNumber, bedRoomNumber, address, longitude, latitude, creationDate)
        updateEstate(updatedEstate)
    }


    private fun getUpdatedEstateEntry(
        id: Long,
        price: Int,
        description : String,
        isNearParks : Boolean,
        isNearShops : Boolean,
        isNearSchools: Boolean,
        isNearHighway: Boolean,
        estateTypePosition: Int,
        estateTypeName: String,
        surface: Int,
        roomNumber: Int,
        bathroomNumber: Int,
        bedRoomNumber: Int,
        address: String,
        longitude : Double,
        latitude : Double,
        creationDate : LocalDate
    ): Estate {
        return Estate(
            id= id,  price = price, description = description, isNearParks = isNearParks, isNearShops = isNearShops, isNearSchools = isNearSchools, isNearHighway = isNearHighway,
            estateTypePosition = estateTypePosition, estateTypeName = estateTypeName, surface = surface, roomNumber = roomNumber, bathroomNumber = bathroomNumber, bedRoomNumber = bedRoomNumber,
            address = address, longitude = longitude, latitude = latitude, creationDate = creationDate
        )
    }



}


