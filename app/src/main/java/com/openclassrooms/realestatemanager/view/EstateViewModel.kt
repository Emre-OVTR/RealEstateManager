package com.openclassrooms.realestatemanager.view

import androidx.lifecycle.*
import com.openclassrooms.realestatemanager.model.Estate
import com.openclassrooms.realestatemanager.repos.EstateRepository
import kotlinx.coroutines.launch

class EstateViewModel(private val repository : EstateRepository) : ViewModel() {

    val allEstates: LiveData<List<Estate>> = repository.allEstates.asLiveData()

    fun insert(estate: Estate) = viewModelScope.launch {
        repository.insert(estate)
    }
}

class EstateViewModelFactory(private val repository: EstateRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EstateViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return EstateViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}