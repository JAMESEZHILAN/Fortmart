package com.fortmart.customer.ui.dashboard.stores.storeList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.fortmart.customer.R
import com.fortmart.customer.databinding.ItemStoreBinding
import com.fortmart.customer.model.other.ProductDetails
import com.fortmart.customer.model.other.StoreDetails

class StoreListRVAdapter(private val mData: Array<StoreDetails>): RecyclerView.Adapter<StoreListRVAdapter.ViewHolder>(){

    var itemClickListener: ((Int, View)->Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemStoreBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = mData.size

    inner class ViewHolder(private val binding: ItemStoreBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int){
            binding.image.setImageDrawable(ContextCompat.getDrawable(binding.root.context, R.drawable.ic_f))
            binding.storeDetails= mData[position]
            binding.root.setOnClickListener{
                itemClickListener?.invoke(position, it)
            }
        }
    }

}