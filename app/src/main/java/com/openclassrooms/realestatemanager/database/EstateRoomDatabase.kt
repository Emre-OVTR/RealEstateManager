package com.openclassrooms.realestatemanager.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.openclassrooms.realestatemanager.Converters
import com.openclassrooms.realestatemanager.model.Estate
import com.openclassrooms.realestatemanager.model.Image
import java.util.*

@Database(entities = [Estate::class, Image::class], version = 1, exportSchema = true)
@TypeConverters(Converters::class)
 abstract class EstateRoomDatabase: RoomDatabase() {

    abstract fun estateDao(): EstateDao
    abstract fun imageDao(): ImageDao

}
