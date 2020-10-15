package com.fortmart.customer.ui.dashboard.stores

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.fortmart.customer.databinding.ItemStoresDashboardMenuBinding
import com.fortmart.customer.model.other.ImageMenu

class StoresMenuRVAdapter(private val mData: Array<ImageMenu>): RecyclerView.Adapter<StoresMenuRVAdapter.ViewHolder>(){

    var itemClickListener: ((Int, View)->Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemStoresDashboardMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = mData.size

    inner class ViewHolder(private val binding: ItemStoresDashboardMenuBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int){
            binding.title.text = mData[position].title
            binding.image.setImageDrawable(ContextCompat.getDrawable(binding.root.context, mData[position].imageId))
            binding.root.setOnClickListener {
                itemClickListener?.invoke(position, it)
            }
        }
    }

}