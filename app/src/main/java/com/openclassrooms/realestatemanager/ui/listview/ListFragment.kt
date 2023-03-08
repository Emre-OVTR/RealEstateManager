package com.openclassrooms.realestatemanager.ui.listview

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
class ListFragment : Fragment()  {

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
        recyclerView = view.findViewById(R.id.estate_list)
        adapter = EstateListAdapter(this::onEstateClicked)
        recyclerView.adapter = adapter

        recyclerView.addItemDecoration(
            DividerItemDecoration(
                activity,
                DividerItemDecoration.VERTICAL
            )
        )

        if (requireArguments().isEmpty) {
            estateViewModel.allEstates.observe(viewLifecycleOwner) { estates ->
                estates?.let { getEstateAndPicture(it) } }
        } else {

            val query = requireArguments().getString("QUERY")
            val myData: Array<Any> =
                arguments?.getStringArray("ARGS")?.map { it.toBooleanOrString() }!!.toTypedArray()

            estateViewModel.getFilteredEstates(query!!, myData)
                .observe(viewLifecycleOwner) { filteredEstates ->
                    filteredEstates?.let { getEstateAndPicture(it) }
                }
        }
            //  val args = requireArguments().getStringArray("ARGS")
            //   val argsList : ArrayList<Any> = ArrayList(args?.toList())



            //   val args: ListFragmentArgs by navArgs()
            //    val filteredEstate = args.FilteredList
            //  val myObject = args.fullEstate


            //  if (args != null) {
            // val args = args.map { it as Any }



       // } else {
       //     Log.e("ddh", "bundle is not null")

         //   val query = requireArguments().getString("QUERY")
        //    val args = requireArguments().getStringArray("ARGS")
         //   val argsList : ArrayList<Any> = ArrayList(args?.toList())

          //  val args: ListFragmentArgs by navArgs()
          //  val filteredEstate = args.FilteredList
          //  val myObject = args.fullEstate


          //  if (args != null) {
                // val args = args.map { it as Any }
            //    estateViewModel.getFilteredEstates(query!!, argsList).observe(viewLifecycleOwner) { filteredestates ->
             //       filteredestates?.let { getEstateAndPicture(it) }
             //   }
          //  } else {
             //   Log.e("args", "args is null")
         //   }
      //  }

    }

    private fun String.toBooleanOrString(): Any = when (this) {
        "true" -> true
        "false" -> false
        else -> this
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
                    .observe(viewLifecycleOwner)  { pictureListLambda ->
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



}






