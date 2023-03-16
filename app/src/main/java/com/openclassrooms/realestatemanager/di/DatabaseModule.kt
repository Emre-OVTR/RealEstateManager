package com.openclassrooms.realestatemanager.di

import android.content.Context
import androidx.room.Room
import com.openclassrooms.realestatemanager.database.EstateDao
import com.openclassrooms.realestatemanager.database.EstateRoomDatabase
import com.openclassrooms.realestatemanager.database.ImageDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Provides
    fun provideEstateDao(estateRoomDatabase: EstateRoomDatabase) : EstateDao {
         return estateRoomDatabase.estateDao()
    }

    @Provides
    fun provideImageDao(estateRoomDatabase: EstateRoomDatabase) : ImageDao {
        return estateRoomDatabase.imageDao()
    }

    @Provides
    @Singleton
    fun provideEstateRoomDatabase(@ApplicationContext appContext : Context):
            EstateRoomDatabase{
        return Room.databaseBuilder(
            appContext,
            EstateRoomDatabase::class.java,
            "estate_database"
        ).build()
    }
}