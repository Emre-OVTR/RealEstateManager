package com.openclassrooms.realestatemanager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class EstateListAdapter : ListAdapter<Estate, EstateListAdapter.EstateViewHolder>(EstatesComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EstateViewHolder {
        return EstateViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: EstateViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.estateType)
    }

    class EstateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val estateItemView: TextView = itemView.findViewById(R.id.text_view)

        fun bind(text: String?) {
            estateItemView.text = text
        }

        companion object {
            fun create(parent: ViewGroup): EstateViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_item, parent, false)
                return EstateViewHolder(view)
            }
        }
    }

    class EstatesComparator : DiffUtil.ItemCallback<Estate>() {
        override fun areItemsTheSame(oldItem: Estate, newItem: Estate): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Estate, newItem: Estate): Boolean {
            return oldItem.estateType == newItem.estateType
        }
    }
}