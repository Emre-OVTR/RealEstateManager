package com.openclassrooms.realestatemanager.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "estate_table")
data class Estate(
    @SerializedName("estate")
   // var estates: List<Estate> = listOf()
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val price: Int,
    val description : String,
      var isNearParks : Boolean,
     val isNearShops : Boolean,
    val isNearSchools: Boolean,
    val isNearHighway: Boolean,
    val estateType: String,
   // val borough: String,
    val surface: Int,
    val roomNumber: Int,
    val bathroomNumber: Int,
    val bedRoomNumber: Int,
    val address: String,
    val longitude : Double,
    val latitude : Double
): Serializable

