package com.openclassrooms.realestatemanager.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

@Entity(foreignKeys = [ForeignKey(entity = Estate::class,
                                  parentColumns = ["id"],
                                  childColumns = ["estateId"])])

data class Image(
                @SerializedName("image")
                @PrimaryKey(autoGenerate = true) val imageId: Long = 0,
                 val imageUri : String,
                 var estateId: Long?) : Serializable
