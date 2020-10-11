package com.base.app.ui.dashboard.stores

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.base.app.databinding.ItemStoresMenuBinding
import com.base.app.model.other.DashboardMenu

class StoresMenuRVAdapter(private val mData: Array<DashboardMenu>): RecyclerView.Adapter<StoresMenuRVAdapter.ViewHolder>(){

    var itemClickListener: ((Int, View)->Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemStoresMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = mData.size

    inner class ViewHolder(private val binding: ItemStoresMenuBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int){
            binding.title.text = mData[position].title
            binding.image.setImageDrawable(ContextCompat.getDrawable(binding.root.context, mData[position].imageId))
            binding.root.setOnClickListener {
                itemClickListener?.invoke(position, it)
            }
        }
    }

}