package com.fortmart.customer.ui.dashboard.items.productList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fortmart.customer.databinding.ItemProductBinding
import com.fortmart.customer.databinding.ItemSubCategoryBinding

class SubCategoryListRVAdapter(private val mData: Array<String>): RecyclerView.Adapter<SubCategoryListRVAdapter.ViewHolder>(){

    var itemClickListener: ((Int, View)->Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemSubCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = mData.size

    inner class ViewHolder(private val binding: ItemSubCategoryBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int){
            binding.title.text= mData[position]
            binding.root.setOnClickListener{
                itemClickListener?.invoke(position, it)
            }
        }
    }

}