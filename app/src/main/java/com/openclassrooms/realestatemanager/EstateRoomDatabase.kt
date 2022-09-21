package com.openclassrooms.realestatemanager

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Estate::class], version = 1, exportSchema = false)
 abstract class EstateRoomDatabase: RoomDatabase() {

    abstract fun estateDao(): EstateDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: EstateRoomDatabase? = null

        fun getDatabase(context: Context,
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


        private class EstateDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {

            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch{
                        var estateDao = database.estateDao()
                        var word = Estate("flat")
                        estateDao.insert(word)

                    }
                }
            }
        }
    }
}
