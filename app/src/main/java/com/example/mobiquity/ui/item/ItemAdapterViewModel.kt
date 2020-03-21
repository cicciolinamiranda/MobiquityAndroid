package com.example.mobiquity.ui.item

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.example.mobiquity.base.BaseViewModel
import com.example.mobiquity.repository.model.Item
import com.example.mobiquity.ui.product.ProductListAdapter

open class ItemAdapterViewModel: BaseViewModel() {
    private val itemId = MutableLiveData<String>()
    private val itemName = MutableLiveData<String>()
    private val description = MutableLiveData<String>()
//    val productListAdapter= ProductListAdapter()
    var item: Item? = null
    private lateinit var listener :ItemAdapterViewModelListener

    fun bind(item: Item){
        this.item = item
        itemId.value = item.id.toString()
        itemName.value = item.name
        description.value = item.description
//        productListAdapter.updateItemList(item.products)
    }

    fun getItemId():MutableLiveData<String>{
        return itemId
    }

    fun getItemName():MutableLiveData<String>{
        return itemName
    }

    fun getItemDescription():MutableLiveData<String>{
        return description
    }

    fun addListener(listener :ItemAdapterViewModelListener) {
        this.listener = listener
    }

    fun onItemClick(item: Item) {
        this.listener?.onItemClick(item)
    }

    interface ItemAdapterViewModelListener {
        fun onItemClick(item: Item)
    }
}