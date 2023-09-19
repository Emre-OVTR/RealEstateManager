package com.openclassrooms.realestatemanager.ui.listview

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.model.Estate
import com.openclassrooms.realestatemanager.model.FullEstate
import com.openclassrooms.realestatemanager.ui.EstateDetailsActivity
import com.openclassrooms.realestatemanager.view.EstateViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private  lateinit var adapter: EstateListAdapter
    private val estateViewModel : EstateViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().title = "Estates List"
        recyclerView = view.findViewById(R.id.estate_list)
        adapter = EstateListAdapter(this::onEstateClicked)
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                activity,
                DividerItemDecoration.VERTICAL
            )
        )
        showEstatesList()
    }

    private fun onEstateClicked(estate: Estate) {
        val intent = Intent(activity, EstateDetailsActivity::class.java)
        intent.putExtra(EstateDetailsActivity.ESTATE, estate)
        startActivity(intent)
    }


     private fun getEstateAndPicture(estateList: List<FullEstate>) {

        if (estateList.isNotEmpty()) {
            for (i in estateList.indices) {
                estateViewModel.getImages(estateList[i].estate.id)
                    .observe(viewLifecycleOwner) { pictureListLambda ->
                        if (pictureListLambda?.size == 0) {
                            Log.e("image", "no image")
                        } else {
                            pictureListLambda?.let {
                                adapter.submitList(estateList)
                                Log.e("update", "List is submited")
                            }
                        }
                    }
            }
        }

    }
    private fun showEstatesList(){
            if (requireArguments().isEmpty) {
                Log.e("vcg", "arguments is empty")
                estateViewModel.allEstates.observe(viewLifecycleOwner) { estates ->
                    estates?.let {
                        if (estates.isNotEmpty()) {
                            getEstateAndPicture(it)
                        } else {
                            Toast.makeText(context, "NO ESTATES AVAILABLE", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            } else {
                filterEstatesList()
            }
        }
    private fun filterEstatesList(){
        val query = requireArguments().getString("QUERY")
        val myData: Array<Any> =
            arguments?.getStringArray("ARGS")?.map { it.toBooleanOrString() }!!.toTypedArray()
        estateViewModel.getFilteredEstates(query!!, myData)
            .observe(viewLifecycleOwner) { filteredEstates ->
                filteredEstates?.let {
                    if (filteredEstates.isNotEmpty()) {
                        getEstateAndPicture(it)
                    } else {
                        Toast.makeText(context, "NO ESTATES AVAILABLE", Toast.LENGTH_LONG)
                            .show()
                    }
                }
            }
    }

    private fun String.toBooleanOrString(): Any = when (this) {
        "true" -> true
        "false" -> false
        else -> this
    }

}








