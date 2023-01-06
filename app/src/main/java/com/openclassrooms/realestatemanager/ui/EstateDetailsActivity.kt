package com.openclassrooms.realestatemanager.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.model.Estate
import com.openclassrooms.realestatemanager.model.Image
import com.openclassrooms.realestatemanager.view.EstateViewModel
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_estate_details.*

@AndroidEntryPoint
class EstateDetailsActivity : AppCompatActivity(), OnMapReadyCallback {


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
    @BindView((R.id.shop_image))
    lateinit var shopImage: ImageView
    @BindView((R.id.school_image))
    lateinit var schoolImage: ImageView
    @BindView((R.id.highway_image))
    lateinit var highwayImage: ImageView

    private lateinit var mMap: GoogleMap
    private  lateinit var recyclerview : RecyclerView
    private lateinit var adapter : EstateDetailsRecyclerViewAdapter
    private val estateViewModel: EstateViewModel by viewModels()
    private lateinit var estate: Estate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_estate_details)
        setSupportActionBar(findViewById(R.id.my_toolbar))
        ButterKnife.bind(this)
        this.configureRecyclerView()
        displayEstate()
        displayEstateImages()
        configureCheckBox()
        configureMap()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
       return when(item.itemId){
           R.id.action_edit ->{
              setContentView(R.layout.edit_estate_mode)
               true
           }
           else -> super.onOptionsItemSelected(item)
       }
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

    private fun displayEstateImages(){
        val image : MutableList<Image> = ArrayList()
        estateViewModel.getImages(estate.id).observe(this) { dbImage ->
            dbImage.let { image.addAll(it)}
            adapter.getImage(image)
        }
    }

    private fun displayEstate(){
        estate = intent.getSerializableExtra(ESTATE) as Estate

        textSurface.text = "${estate.surface} m²"
        textNumberOfRooms.text = estate.roomNumber.toString()
        textNumberOfBathrooms.text = estate.bathroomNumber.toString()
        textNumberOfBedrooms.text = estate.bedRoomNumber.toString()
        textAddress.text = estate.address
        textDescription.text = estate.description
    }

    private fun configureCheckBox(){

        when {
            estate.isNearParks -> park_image.setImageResource(R.drawable.ic_baseline_check_circle_24)
            else -> park_image.setImageResource(R.drawable.ic_baseline_clear_24)
        }
        when {
            estate.isNearHighway -> highwayImage.setImageResource(R.drawable.ic_baseline_check_circle_24)
            else -> highwayImage.setImageResource(R.drawable.ic_baseline_clear_24)
        }
        when {
            estate.isNearSchools -> schoolImage.setImageResource(R.drawable.ic_baseline_check_circle_24)
            else -> schoolImage.setImageResource(R.drawable.ic_baseline_clear_24)
        }
        when {
            estate.isNearShops-> shopImage.setImageResource(R.drawable.ic_baseline_check_circle_24)
            else -> shopImage.setImageResource(R.drawable.ic_baseline_clear_24)
        }
    }

    private fun configureMap(){
        val mapFragment = supportFragmentManager.findFragmentById(R.id.detail_fragment_map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }
}












