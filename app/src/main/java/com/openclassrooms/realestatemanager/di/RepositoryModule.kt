package com.openclassrooms.realestatemanager.di

import com.openclassrooms.realestatemanager.database.EstateDao
import com.openclassrooms.realestatemanager.database.ImageDao
import com.openclassrooms.realestatemanager.repos.EstateRepository
import com.openclassrooms.realestatemanager.repos.ImageRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {

    @Provides
    fun provideEstateRepository(estateDao: EstateDao) : EstateRepository = EstateRepository(estateDao)

    @Provides
    fun provideImageRepository(imageDao: ImageDao): ImageRepository = ImageRepository(imageDao)

}