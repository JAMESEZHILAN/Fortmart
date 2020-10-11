package com.base.app.ui.dashboard.stores.storeList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.base.app.R
import com.base.app.databinding.ItemStoreListBinding
import com.base.app.model.other.StoreDetails

class StoreListRVAdapter(private val mData: Array<StoreDetails>): RecyclerView.Adapter<StoreListRVAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemStoreListBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mData[position])
    }

    override fun getItemCount(): Int = mData.size

    class ViewHolder(private val binding: ItemStoreListBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(data: StoreDetails){
            binding.image.setImageDrawable(ContextCompat.getDrawable(binding.root.context, R.drawable.ic_f))
            binding.storeDetails = data
        }
    }

}