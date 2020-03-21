package com.example.mobiquity.ui.item

import androidx.lifecycle.MutableLiveData
import com.example.mobiquity.base.BaseViewModel
import com.example.mobiquity.repository.model.Item
import com.example.mobiquity.repository.model.Product
import java.util.*

class ItemAdapterViewModel: BaseViewModel() {
    private val itemId = MutableLiveData<String>()
    private val itemName = MutableLiveData<String>()
    private val description = MutableLiveData<String>()
    private val products = MutableLiveData<List<Product>>();

    fun bind(item: Item){
        itemId.value = item.id.toString()
        itemName.value = item.name
        description.value = item.description
        products.value = item.products
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

    fun getItemProducts():MutableLiveData<List<Product>>{
        return products
    }

}