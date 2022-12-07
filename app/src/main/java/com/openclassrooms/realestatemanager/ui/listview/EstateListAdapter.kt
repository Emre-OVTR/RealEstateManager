package com.openclassrooms.realestatemanager.ui.listview

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
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
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.model.Estate
import com.openclassrooms.realestatemanager.model.FullEstate
import com.openclassrooms.realestatemanager.model.Image
import kotlinx.android.synthetic.main.fragment_list.view.*
import kotlinx.android.synthetic.main.recyclerview_item.view.*
import kotlin.math.absoluteValue

class EstateListAdapter(private val clickListener: (estate: Estate)-> Unit) : ListAdapter<FullEstate, EstateListAdapter.EstateViewHolder>(
    MyDiffUtil()
) {



// Le probleme c'est que l'image chargé n'a aucun lien avec notre estate mise a part le font qu'ils ont le meme id imageid / ESTATEID


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EstateViewHolder {
        return EstateViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: EstateViewHolder, position: Int) {
        val estate = getItem(position)
       // val image = Image(imageUri = imageList.get(position).imageUri, estateId = getItemId(position))

        holder.bind(estate, clickListener)

    }



    class EstateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val estateItemView: TextView = itemView.findViewById(R.id.text_view)
        private val estateItemPrice: TextView = itemView.findViewById(R.id.list_item_price)
        private val estateItemBorough: TextView = itemView.findViewById(R.id.list_item_city)
        private val estateImage : ImageView = itemView.findViewById(R.id.list_item_main_pic)


        fun bind(estate: FullEstate, clickListener: (estate: Estate) -> Unit) = with(itemView) {
           // if (image) {
                Glide.with(context).load(Uri.parse(estate.images[0].imageUri)).apply(RequestOptions().centerCrop()).into(estateImage)
           // } else {
          //      Glide.with(context).load(R.drawable.ic_no_image_available).apply(RequestOptions().centerCrop()).into(estateImage)
         //   }

            estateItemView.text = estate.estate.estateType
            estateItemPrice.text = estate.estate.price.toString()
            estateItemBorough.text = estate.estate.borough

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


 //   @SuppressLint("NotifyDataSetChanged")
   /// fun updateImage(imageList: List<Image>) {

   ///     this.imageList = imageList as MutableList<Image>
    //    this.notifyDataSetChanged()
   // }


    class MyDiffUtil : DiffUtil.ItemCallback<FullEstate>() {
        override fun areItemsTheSame(oldItem: FullEstate, newItem: FullEstate): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: FullEstate, newItem: FullEstate): Boolean {
            return oldItem.estate.id == newItem.estate.id
        }
    }


}




