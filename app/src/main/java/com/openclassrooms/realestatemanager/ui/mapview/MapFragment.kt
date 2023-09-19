package com.openclassrooms.realestatemanager.ui.mapview

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.model.Estate
import com.openclassrooms.realestatemanager.model.FullEstate
import com.openclassrooms.realestatemanager.ui.EstateDetailsActivity
import com.openclassrooms.realestatemanager.view.EstateViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private val estateViewModel : EstateViewModel by viewModels()
    private val estatesList : MutableList<FullEstate> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().setTitle("Google Map")
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        estateViewModel.allEstates.observe(viewLifecycleOwner) { estates ->
            estates.let { estatesList.addAll(estates) }
        }
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setOnMarkerClickListener(this::onMarkerClick)

        if (estatesList.isNotEmpty()){
        for (i in estatesList.indices){
            val latitude : Double = estatesList[i].estate.latitude
            val longitude : Double = estatesList[i].estate.longitude
            val locations = LatLng(latitude, longitude)
            val marker: Marker? =  mMap.addMarker(MarkerOptions().position(locations).title(estatesList[i].estate.address))

            mMap.moveCamera(CameraUpdateFactory.newLatLng(locations))
            marker?.tag = estatesList[i].estate

        }} else {
            val sydney = LatLng(-34.0, 151.0)
            mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        }
    }

     private fun onMarkerClick(marker: Marker): Boolean {
        val intent = Intent(activity, EstateDetailsActivity::class.java)
        intent.putExtra(EstateDetailsActivity.ESTATE, marker.tag as Estate)
        startActivity(intent)
        return false
    }

}