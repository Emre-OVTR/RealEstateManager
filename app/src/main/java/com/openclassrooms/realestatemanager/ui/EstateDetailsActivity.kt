package com.openclassrooms.realestatemanager.ui

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import butterknife.BindView
import butterknife.ButterKnife
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.model.Estate

class EstateDetailsActivity : AppCompatActivity() {


    @BindView((R.id.detail_fragment_surface))
    lateinit var textSurface : TextView
    @BindView((R.id.detail_fragment_rooms))
    lateinit var textNumberOfRooms : TextView
    @BindView((R.id.detail_fragment_bathrooms))
    lateinit var textNumberOfBathrooms : TextView
    @BindView((R.id.detail_fragment_bedrooms))
    lateinit var textNumberOfBedrooms : TextView
    @BindView((R.id.detail_fragment_location_address))
    lateinit var textAdress : TextView



    lateinit var estate: Estate.EstateEntity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_estate_details)
        setSupportActionBar(findViewById(R.id.my_toolbar))
        ButterKnife.bind(this)

        estate = intent.getSerializableExtra(ESTATE) as Estate.EstateEntity

        textSurface.text = estate.surface.toString()
        textNumberOfRooms.text = estate.roomNumber.toString()
        textNumberOfBathrooms.text = estate.bathroomNumber.toString()
        textNumberOfBedrooms.text = estate.bedRoomNumber.toString()
        textAdress.text = estate.address

    }

    companion object{
        const val ESTATE = "ESTATE"
    }
}












