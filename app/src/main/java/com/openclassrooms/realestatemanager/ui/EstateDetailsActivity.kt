package com.openclassrooms.realestatemanager.ui

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.openclassrooms.realestatemanager.EstateDetailsRecyclerViewAdapter
import com.openclassrooms.realestatemanager.EstatesApplication
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.model.Estate
import com.openclassrooms.realestatemanager.model.Image
import com.openclassrooms.realestatemanager.view.EstateViewModel
import com.openclassrooms.realestatemanager.view.EstateViewModelFactory

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
    lateinit var textAddress : TextView
    @BindView((R.id.detail_fragment_desc))
    lateinit var textDescription : TextView
    @BindView((R.id.park_image))
    lateinit var imagePark : ImageView
    private  lateinit var recyclerview : RecyclerView
    private lateinit var adapter : EstateDetailsRecyclerViewAdapter
    private val estateViewModel: EstateViewModel by viewModels {
        EstateViewModelFactory(((application as EstatesApplication).repository), ((application as EstatesApplication).imageRepository))
    }

    lateinit var estate: Estate.EstateEntity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_estate_details)
        setSupportActionBar(findViewById(R.id.my_toolbar))
        ButterKnife.bind(this)
        this.configureRecyclerView()

        estate = intent.getSerializableExtra(ESTATE) as Estate.EstateEntity

        textSurface.text = estate.surface.toString()
        textNumberOfRooms.text = estate.roomNumber.toString()
        textNumberOfBathrooms.text = estate.bathroomNumber.toString()
        textNumberOfBedrooms.text = estate.bedRoomNumber.toString()
        textAddress.text = estate.address


        val image : MutableList<Image> = ArrayList()
        estateViewModel.getImages(estate.id).observe(this) { dbImage ->
            dbImage.let { image.add(it[0]) }
            adapter.getImage(image)
        }


    }

    companion object{
        const val ESTATE = "ESTATE"

    }

    private fun configureRecyclerView(){
        recyclerview = findViewById(R.id.detail_fragment_recycler_view)
        adapter = EstateDetailsRecyclerViewAdapter()
        recyclerview.adapter = adapter
        recyclerview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }
}












