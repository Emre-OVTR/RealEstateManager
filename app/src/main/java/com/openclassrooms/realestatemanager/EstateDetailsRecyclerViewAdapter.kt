package com.openclassrooms.realestatemanager

import android.annotation.SuppressLint
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.openclassrooms.realestatemanager.model.Image

class EstateDetailsRecyclerViewAdapter : RecyclerView.Adapter<EstateDetailsRecyclerViewAdapter.EstateDetailsViewHolder>() {

    private var uriOfEstateImage : MutableList<Image> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EstateDetailsViewHolder {
        val view  = LayoutInflater.from(parent.context)
            .inflate(R.layout.estate_details_recyclerview_item, parent, false)
        return EstateDetailsViewHolder(view)
    }

    override fun onBindViewHolder(holder: EstateDetailsViewHolder, position: Int)  {
        val image =uriOfEstateImage[position]
        holder.bind(image)
     //  val uri : Uri  = Uri.parse(Uri.parse(image.imageUri).path)
    //    holder.picture.setImageURI(uri)
    //    holder.glide.load(uri).into(holder.itemView.savedImageView)
    }


    override fun getItemCount(): Int {
        return uriOfEstateImage.size
    }

   // fun getImage(position: Int) : Image{
   //     return uriOfEstateImage[position]
  //  }

   @SuppressLint("NotifyDataSetChanged")
   fun getImage(images : List<Image>){
       this.uriOfEstateImage = images as MutableList<Image>
        this.notifyDataSetChanged()
    }

    class EstateDetailsViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
            private val picture :ImageView = itemView.findViewById(R.id.savedImageView)
           // val glide : RequestManager = Glide.with(itemView)
        fun bind (image: Image) = with(itemView){
           // glide.load(Uri.parse(image.imageUri)).into(picture)
            Glide.with(context).load(Uri.parse(image.imageUri)).into(picture)
        }
    }
}