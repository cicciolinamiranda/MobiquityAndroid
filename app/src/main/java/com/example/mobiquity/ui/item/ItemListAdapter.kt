package com.example.mobiquity.ui.item

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mobiquity.R
import com.example.mobiquity.databinding.ItemBinding
import com.example.mobiquity.repository.model.Item

class ItemListAdapter: RecyclerView.Adapter<ItemListAdapter.ViewHolder>() {
    private lateinit var itemList:List<Item>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemListAdapter.ViewHolder {
        val binding: ItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemListAdapter.ViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int {
        return if(::itemList.isInitialized) itemList.size else 0
    }

    fun updateItemList(postList:List<Item>){
        this.itemList = postList
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ItemBinding):RecyclerView.ViewHolder(binding.root){
        private val viewModel = ItemAdapterViewModel()

        fun bind(item:Item){
            viewModel.bind(item)
            binding.viewModel = viewModel
        }
    }
}