package com.openclassrooms.realestatemanager.ui

import android.annotation.SuppressLint
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.model.Image

class EstateDetailsRecyclerViewAdapter : RecyclerView.Adapter<EstateDetailsRecyclerViewAdapter.EstateDetailsViewHolder>() {

    private var uriOfEstateImage : MutableList<Image> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EstateDetailsViewHolder {
        val view  = LayoutInflater.from(parent.context)
            .inflate(R.layout.estate_details_recyclerview_item, parent, false)
        view.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        return EstateDetailsViewHolder(view)
    }

    override fun onBindViewHolder(holder: EstateDetailsViewHolder, position: Int)  {
        val image =uriOfEstateImage[position]
        holder.bind(image)
    }

    override fun getItemCount(): Int {
        return uriOfEstateImage.size
    }

   @SuppressLint("NotifyDataSetChanged")
   fun getImage(images : List<Image>){
       this.uriOfEstateImage = images as MutableList<Image>
        this.notifyDataSetChanged()
    }

    class EstateDetailsViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
            private val picture :ImageView = itemView.findViewById(R.id.savedImageView)
        fun bind (image: Image) = with(itemView){
            Glide.with(context).load(Uri.parse(image.imageUri)).into(picture)
        }
    }
}