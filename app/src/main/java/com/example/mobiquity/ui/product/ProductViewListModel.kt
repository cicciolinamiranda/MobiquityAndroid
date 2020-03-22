package com.example.mobiquity.ui.product

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.example.mobiquity.R
import com.example.mobiquity.base.BaseViewModel
import com.example.mobiquity.network.ItemApi
import com.example.mobiquity.repository.dao.ItemDao
import com.example.mobiquity.repository.dto.ProductDTO
import com.example.mobiquity.repository.model.Item
import com.example.mobiquity.repository.model.Product
import com.example.mobiquity.repository.model.converters.Converters
import com.example.mobiquity.ui.item.ItemListAdapter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ProductViewListModel(): BaseViewModel() {

    constructor (itemDao: ItemDao) : this() {
        this.itemDao = itemDao
    }

    lateinit var itemDao: ItemDao

    val productListAdapter: ProductListAdapter = ProductListAdapter()

    private lateinit var subscription: Disposable
    val showList: MutableLiveData<Int> = MutableLiveData()
    val itemName: MutableLiveData<String> = MutableLiveData()

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }

    fun loadItems(itemId: Long){
        subscription = Observable.fromCallable {
            itemDao.getProductsOnlyByItemId(itemId)
        }
            .concatMap {
                    itemProducts ->
                Observable.just(itemProducts)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                        result -> onRetrieveItemSuccess(result as ProductDTO)
                },
                {
                    onRetrieveItemError()
                }
            )

    }

    private fun onRetrieveItemSuccess(itemProducts: ProductDTO) {
        showList.value = View.VISIBLE
        productListAdapter.updateItemList(Converters.toProduct(itemProducts.products))
        itemName.value = itemProducts.itemName
    }

    private fun onRetrieveItemError(){
        showList.value = View.GONE
    }

}