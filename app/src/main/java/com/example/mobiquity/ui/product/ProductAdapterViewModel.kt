package com.example.mobiquity.ui.product

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.example.mobiquity.base.BaseViewModel
import com.example.mobiquity.repository.model.Product
import com.example.mobiquity.utils.BASE_URL

class ProductAdapterViewModel: BaseViewModel() {
    private val productName = MutableLiveData<String>()
    private val productDescription = MutableLiveData<String>()
    private val productImageUrl = MutableLiveData<String>()
    private val productAmount = MutableLiveData<String>()
    private val productCurrency = MutableLiveData<String>()
    private val displayProductDesc = MutableLiveData<Int>()

    fun bind(product: Product){
        productName.value = product.name
        productDescription.value = product.description
        productAmount.value = product.salePrice.amount.toString() ;
        productImageUrl.value = product.url;
        productCurrency.value = product.salePrice.currency;
        displayProductDesc.value = if (product.description.isEmpty()) View.GONE else View.VISIBLE
    }

    fun getProductName():MutableLiveData<String>{
        return productName
    }

    fun getProductDescription():MutableLiveData<String>{
        return productDescription
    }

    fun getProductImageUrl():MutableLiveData<String>{
        return productImageUrl
    }

    fun getProductAmount():MutableLiveData<String>{
        return productAmount
    }

    fun getProductCurrency():MutableLiveData<String>{
        return productCurrency
    }

    fun showProductDesc():MutableLiveData<Int>{
        return displayProductDesc
    }

}