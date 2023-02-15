package com.openclassrooms.realestatemanager.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.google.android.gms.maps.model.LatLng
import com.openclassrooms.realestatemanager.Converters
import com.openclassrooms.realestatemanager.model.Estate
import com.openclassrooms.realestatemanager.model.Image
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@Database(entities = [Estate::class, Image::class], version = 1, exportSchema = true)
@TypeConverters(Converters::class)
 abstract class EstateRoomDatabase: RoomDatabase() {

    abstract fun estateDao(): EstateDao
    abstract fun imageDao(): ImageDao

    private class EstateDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch{
                    val estateDao = database.estateDao()
                    val estate = Estate(0,150000,
                        estateTypePosition = 1,
                       // borough = "Manhattan",
                        description = "bdchbchbcskbhdbckbbshbcdjk",
                        surface = 150,
                        roomNumber = 8,
                        bathroomNumber = 2,
                        bedRoomNumber = 4,
                        address = "8 rue Jean",
                    longitude = 34.00, latitude = 34.00, isNearHighway = true, isNearShops = true, isNearParks = true, isNearSchools = true,
                    creationDate = LocalDate.now(),
                        estateTypeName = "Flat",
                        isSold = true//coordinate = "2"
                    )
          //              estateDao.insert(estate)
                //    val imageDao = database.imageDao()
                //    val image = Image(0,"content://media/external/images/media/1000000034" , estate.id)
                //    imageDao.insert(image)
                }
            }
        }
    }

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: EstateRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): EstateRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EstateRoomDatabase::class.java,
                    "estate_database"
                ).addCallback(EstateDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
