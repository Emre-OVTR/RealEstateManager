package com.openclassrooms.realestatemanager.ui

import android.os.Bundle
import android.view.Menu
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.openclassrooms.realestatemanager.EstateDetailsRecyclerViewAdapter
import com.openclassrooms.realestatemanager.EstatesApplication
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.model.Estate
import com.openclassrooms.realestatemanager.model.Image
import com.openclassrooms.realestatemanager.view.EstateViewModel
import com.openclassrooms.realestatemanager.view.EstateViewModelFactory
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.model.Place

class EstateDetailsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
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

    lateinit var estate: Estate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_estate_details)
        setSupportActionBar(findViewById(R.id.my_toolbar))
        ButterKnife.bind(this)
        this.configureRecyclerView()

        estate = intent.getSerializableExtra(ESTATE) as Estate

        textSurface.text = estate.surface.toString()
        textNumberOfRooms.text = estate.roomNumber.toString()
        textNumberOfBathrooms.text = estate.bathroomNumber.toString()
        textNumberOfBedrooms.text = estate.bedRoomNumber.toString()
        textAddress.text = estate.address




        val image : MutableList<Image> = ArrayList()
        estateViewModel.getImages(estate.id).observe(this) { dbImage ->
            dbImage.let { image.addAll(it)}
            adapter.getImage(image)
        }

        val mapFragment = supportFragmentManager.findFragmentById(R.id.detail_fragment_map) as SupportMapFragment
        mapFragment.getMapAsync(this)



    }

    companion object{
        const val ESTATE = "ESTATE"

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_estate_details_appbar, menu
        )
        return super.onCreateOptionsMenu(menu)
    }

    private fun configureRecyclerView(){
        recyclerview = findViewById(R.id.detail_fragment_recycler_view)
        adapter = EstateDetailsRecyclerViewAdapter()
        recyclerview.adapter = adapter
        recyclerview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val latitude : Double = estate.latitude
        val longitude : Double = estate.longitude
        val sydney = LatLng(latitude, longitude)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }
}












