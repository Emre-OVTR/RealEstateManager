package com.openclassrooms.realestatemanager.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.openclassrooms.realestatemanager.Converters
import java.io.Serializable
import java.time.LocalDate
import java.util.*

@Entity(tableName = "estate_table")
@TypeConverters(Converters::class)
data class Estate(
    //enlever la ligne 19
 //   @SerializedName("estate")
   // var estates: List<Estate> = listOf()
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val price: Int,
    val description : String,
      var isNearParks : Boolean,
     val isNearShops : Boolean,
    val isNearSchools: Boolean,
    val isNearHighway: Boolean,
    val estateTypePosition: Int,
    val estateTypeName: String,
   // val borough: String,
    val surface: Int,
    val roomNumber: Int,
    val bathroomNumber: Int,
    val bedRoomNumber: Int,
    val address: String,
    val longitude : Double,
    val latitude : Double,
    val creationDate : LocalDate,
   // val coordinate : String
): Serializable

