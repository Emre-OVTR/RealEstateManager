package com.openclassrooms.realestatemanager.ui.listview

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.model.Estate
import com.openclassrooms.realestatemanager.model.FullEstate

class EstateListAdapter(private val clickListener: (estate: Estate)-> Unit) : ListAdapter<FullEstate, EstateListAdapter.EstateViewHolder>(
    MyDiffUtil()
) {






    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EstateViewHolder {
        return EstateViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: EstateViewHolder, position: Int) {
        val estate = getItem(position)
        holder.bind(estate, clickListener)

    }

    class EstateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val estateItemView: TextView = itemView.findViewById(R.id.text_view)
        private val estateItemPrice: TextView = itemView.findViewById(R.id.list_item_price)
        private var estateItemSold: TextView = itemView.findViewById(R.id.list_item_sold)
        private val estateImage : ImageView = itemView.findViewById(R.id.list_item_main_pic)


        fun bind(estate: FullEstate, clickListener: (estate: Estate) -> Unit) = with(itemView) {
            Glide.with(context).load(Uri.parse(estate.images[0].imageUri)).apply(RequestOptions().centerCrop()).into(estateImage)
            estateItemView.text = estate.estate.estateTypeName
            estateItemPrice.text =  "$${estate.estate.price}"

            if (estate.estate.isSold){
                estateItemSold.text = context.getString(R.string.string_sold)
            } else {
                estateItemSold.text = context.getString(R.string.string_forsale)
            }
            itemView.setOnClickListener{
                clickListener(estate.estate)
            }
        }

        companion object {
            fun create(parent: ViewGroup): EstateViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_item, parent, false)
                return EstateViewHolder(view)
            }
        }
    }

    class MyDiffUtil : DiffUtil.ItemCallback<FullEstate>() {
        override fun areItemsTheSame(oldItem: FullEstate, newItem: FullEstate): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: FullEstate, newItem: FullEstate): Boolean {
            return oldItem.estate.id == newItem.estate.id
        }
    }


}




