package com.openclassrooms.realestatemanager.ui.listview

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.model.Estate
import com.openclassrooms.realestatemanager.model.Image
import kotlinx.android.synthetic.main.recyclerview_item.view.*

class EstateListAdapter(private val clickListener: (estate: Estate.EstateEntity)-> Unit) : ListAdapter<Estate.EstateEntity, EstateListAdapter.EstateViewHolder>(
    MyDiffUtil()
) {


    private var imageList: MutableList<Image?> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EstateViewHolder {
        return EstateViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: EstateViewHolder, position: Int) {
        val estate = getItem(position)
        holder.bind(estate, imageList[position], clickListener)

    }



    class EstateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val estateItemView: TextView = itemView.findViewById(R.id.text_view)
        private val estateItemPrice: TextView = itemView.findViewById(R.id.list_item_price)
        private val estateItemBorough: TextView = itemView.findViewById(R.id.list_item_city)
        private val glide : RequestManager = Glide.with(itemView)



        fun bind(estate: Estate.EstateEntity,image : Image?, clickListener: (estate: Estate.EstateEntity) -> Unit) {
            estateItemView.text = estate.estateType
            estateItemPrice.text = estate.price.toString()
            estateItemBorough.text = estate.borough
            if (image != null) {
                glide.load(Uri.parse(image.imageUri)).apply(RequestOptions().centerCrop()).into(itemView.list_item_main_pic)
            }

            itemView.setOnClickListener{
                clickListener(estate)
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

    fun updateImage(imageList: List<Image?>) {

        this.imageList = imageList as MutableList<Image?>
        this.notifyDataSetChanged()
    }


    class MyDiffUtil : DiffUtil.ItemCallback<Estate.EstateEntity>() {
        override fun areItemsTheSame(oldItem: Estate.EstateEntity, newItem: Estate.EstateEntity): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Estate.EstateEntity, newItem: Estate.EstateEntity): Boolean {
            return oldItem.id == newItem.id
        }
    }




}




