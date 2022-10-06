package com.openclassrooms.realestatemanager.ui.listview

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.EstatesApplication
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.ui.EstateDetails
import com.openclassrooms.realestatemanager.ui.EstateListAdapter
import com.openclassrooms.realestatemanager.view.EstateViewModel
import com.openclassrooms.realestatemanager.view.EstateViewModelFactory


class ListFragment : Fragment() {


    private lateinit var adapter:EstateListAdapter
    private lateinit var recyclerView: RecyclerView



     private val estateViewModel: EstateViewModel by viewModels {
        EstateViewModelFactory((activity?.application as EstatesApplication).repository)

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {


        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        recyclerView= view.findViewById(R.id.estate_list)
        adapter = EstateListAdapter {
            val intent = Intent(activity, EstateDetails::class.java)
            startActivity(intent)
            true

        }
        recyclerView.adapter = adapter



        estateViewModel.allEstates.observe(viewLifecycleOwner, Observer { estates ->
            estates?.let { adapter.submitList(it) }
        })

    }

 }




