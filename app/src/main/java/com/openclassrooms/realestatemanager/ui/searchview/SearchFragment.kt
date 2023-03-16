package com.openclassrooms.realestatemanager.ui.searchview

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.openclassrooms.realestatemanager.EstateStatus
import com.openclassrooms.realestatemanager.EstateType
import com.openclassrooms.realestatemanager.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_search.*

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var fragmentSearchBinding: FragmentSearchBinding
    private lateinit var typeSpinner: Spinner
    private lateinit var statusSpinner: Spinner
    private var listOfTypes = EstateType.values()
    private var listOfStatus = EstateStatus.values()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentSearchBinding = FragmentSearchBinding.inflate(layoutInflater)
        configureSpinners()
        fragmentSearchBinding.searchFragmentFAB.setOnClickListener {
            buildSearchQuery()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return fragmentSearchBinding.root
    }

    private fun configureSpinners() {
        typeSpinner = fragmentSearchBinding.estateTypeSpinner
        val typeArrayAdapter =
            ArrayAdapter(requireContext(), R.layout.simple_spinner_item, listOfTypes)
        typeSpinner.adapter = typeArrayAdapter

        statusSpinner = fragmentSearchBinding.estateStatusSpinner
        val statusArrayAdapter =
            ArrayAdapter(requireContext(), R.layout.simple_spinner_item, listOfStatus)
        statusSpinner.adapter = statusArrayAdapter
        statusSpinner.setSelection(2)
    }


    private fun buildSearchQuery() {

        val estateTypePosition = estateType_spinner.selectedItemPosition
        val estateType = estateType_spinner.selectedItem.toString()
        val estateStatus = estateStatus_spinner.selectedItemPosition
        val estatePriceMin = search_fragment_price_min.text.toString().toIntOrNull()
        val estatePriceMax = search_fragment_price_max.text.toString().toIntOrNull()
        val estateSurfaceMin = search_fragment_surface_min.text.toString().toIntOrNull()
        val estateSurfaceMax = search_fragment_surface_max.text.toString().toIntOrNull()
        val estateBedroomMin = search_fragment_bedrooms_min.text.toString().toIntOrNull()
        val estateBedroomMax = search_fragment_bedrooms_max.text.toString().toIntOrNull()
        val estatePark = search_fragment_nearby_parks.isChecked
        val estateHighway = search_fragment_nearby_highway.isChecked
        val estateSchool = search_fragment_nearby_schools.isChecked
        val estateShop = search_fragment_nearby_shops.isChecked
        var query = "SELECT * FROM estate_table"
        val args = arrayListOf<Any>()
        var conditions = false


        if (estateTypePosition > 0) {

            query += if (conditions) " AND " else " WHERE "; conditions = true
            query += "estateTypeName =:${estateType}"
            args.add(estateType)
        }

        if (estateStatus != 2) {

            query += if (conditions) " AND " else " WHERE "; conditions = true
            query += "isSold =:$estateStatus"
            args.add(estateStatus)
        }


        if (estatePriceMin != null) {
            query += if (conditions) " AND " else " WHERE "; conditions = true
            query += "price >= :${estatePriceMin.toInt()}"
            args.add(estatePriceMin.toInt())
        }

        if (estatePriceMax != null) {
            query += if (conditions) " AND " else " WHERE "; conditions = true
            query += "price <= :${estatePriceMax.toInt()}"
            args.add(estatePriceMax.toInt())
        }

        if (estateSurfaceMin != null) {
            query += if (conditions) " AND " else " WHERE "; conditions = true
            query += "surface >= :$estateSurfaceMin"
            args.add(estateSurfaceMin)
        }

        if (estateSurfaceMax != null) {
            query += if (conditions) " AND " else " WHERE "; conditions = true
            query += "surface <= :$estateSurfaceMax"
            args.add(estateSurfaceMax)
        }

        if (estateBedroomMin != null) {
            query += if (conditions) " AND " else " WHERE "; conditions = true
            query += "bedroomNumber >= :$estateBedroomMin"
            args.add(estateBedroomMin)
        }

        if (estateBedroomMax != null) {
            query += if (conditions) " AND " else " WHERE "; conditions = true
            query += "bedroomNumber <= :$estateBedroomMax"
            args.add(estateBedroomMax)
        }

        if (estatePark) {
            query += if (conditions) " AND " else " WHERE "; conditions = true
            query += "isNearParks = :$estatePark"
            args.add(estatePark)
        }

        if (estateShop) {
            query += if (conditions) " AND " else " WHERE "; conditions = true
            query += "isNearShops = :$estateShop"
            args.add(estateShop)
        }

        if (estateSchool) {
            query += if (conditions) " AND " else " WHERE "; conditions = true
            query += "isNearSchools = :$estateSchool"
            args.add(estateSchool)
        }

        if (estateHighway) {
            query += if (conditions) " AND " else " WHERE "; conditions = true
            query += "isNearHighway = :$estateHighway"
            args.add(estateHighway)
        }

        if(args.isEmpty()){
            Toast.makeText(context,"Can't filter List without arguments", Toast.LENGTH_LONG).show()
        } else {
            launchListFragment(query, args.toArray())
        }
    }



    private fun launchListFragment(query:String, args: Array<Any>) {
        val argsTyped = args.map { it.toString() }.toTypedArray()
        findNavController().navigate(com.openclassrooms.realestatemanager.R.id.action_searchFragment_to_listFragment, Bundle().apply {
            putString("QUERY", query)
            putStringArray("ARGS", argsTyped)
    })
}
}