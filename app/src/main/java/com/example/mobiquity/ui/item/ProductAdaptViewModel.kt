package com.example.mobiquity.ui.product

import androidx.lifecycle.MutableLiveData
import com.example.mobiquity.base.BaseViewModel
import com.example.mobiquity.repository.model.Product

class ProductAdaptViewModel: BaseViewModel() {
    private val productName = MutableLiveData<String>()
    private val productDescription = MutableLiveData<String>()
    private val productImageUrl = MutableLiveData<String>()
    private val productAmount = MutableLiveData<Double>();
    private val productCurrency = MutableLiveData<String>();

    fun bind(product: Product){
        productName.value = product.name
        productDescription.value = product.description
        productAmount.value = product.salePrice.amount;
        productImageUrl.value = product.url;
        productCurrency.value = product.salePrice.currency;
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

    fun getProductAmount():MutableLiveData<Double>{
        return productAmount
    }

    fun getProductCurrency():MutableLiveData<String>{
        return productCurrency
    }

}