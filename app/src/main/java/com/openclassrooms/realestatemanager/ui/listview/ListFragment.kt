package com.openclassrooms.realestatemanager.ui.listview

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.EstatesApplication
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.model.Estate
import com.openclassrooms.realestatemanager.model.Image
import com.openclassrooms.realestatemanager.ui.EstateDetailsActivity
import com.openclassrooms.realestatemanager.view.EstateViewModel
import com.openclassrooms.realestatemanager.view.EstateViewModelFactory


class ListFragment() : Fragment() {


    private lateinit var adapter: EstateListAdapter
    private lateinit var recyclerView: RecyclerView


    private val estateViewModel: EstateViewModel by viewModels {
        EstateViewModelFactory(
            ((activity?.application as EstatesApplication).repository),
            ((activity?.application as EstatesApplication).imageRepository)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        recyclerView = view.findViewById(R.id.estate_list)
        adapter = EstateListAdapter(this::onEstateClicked)
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                activity,
                DividerItemDecoration.VERTICAL
            )
        )
        estateViewModel.allEstates.observe(viewLifecycleOwner) { estates ->
            estates?.let { getPicture(it) }
        }

    }

    private fun onEstateClicked(estate: Estate.EstateEntity) {
        val intent = Intent(activity, EstateDetailsActivity::class.java)
        intent.putExtra(EstateDetailsActivity.ESTATE, estate)
        startActivity(intent)
    }


    private fun getPicture(estateList: List<Estate.EstateEntity>) {
        val imageList: MutableList<Image?> = ArrayList()
        if (estateList.isNotEmpty()) {
            for (i in estateList.indices) {
                estateViewModel.getImages(estateList[i].id)
                    .observe(viewLifecycleOwner, Observer { pictureListLambda ->
                        if (pictureListLambda?.size == 0) {
                            imageList.add(null)
                        } else {
                            pictureListLambda?.let { imageList.add(it[0]) }
                        }
                        adapter.submitList(estateList)
                        adapter.updateImage(imageList)

                    })
            }
        }
    }


}




