package com.example.mobiquity.ui.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mobiquity.R
import com.example.mobiquity.databinding.ProductBinding
import com.example.mobiquity.repository.model.Product

class ProductListAdapter: RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {

    private lateinit var productList:List<Product>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ProductBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.product, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(productList[position])
    }

    override fun getItemCount(): Int {
        return if(::productList.isInitialized) productList.size else 0
    }

    fun updateItemList(postList:List<Product>){
        this.productList = postList
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ProductBinding): RecyclerView.ViewHolder(binding.root){
        private val viewModel = ProductAdapterViewModel()

        fun bind(product: Product){
            viewModel.bind(product)
            binding.viewModel = viewModel
        }
    }
}