package com.example.mobiquity.ui.product

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.example.mobiquity.R
import com.example.mobiquity.base.BaseViewModel
import com.example.mobiquity.network.ItemApi
import com.example.mobiquity.repository.dao.ItemDao
import com.example.mobiquity.repository.model.Item
import com.example.mobiquity.ui.item.ItemListAdapter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ProductViewListModel(private val itemDao: ItemDao): BaseViewModel() {

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
            itemDao.getById(itemId)
        }
            .concatMap {
                    item ->
                    Observable.just(item)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                        result -> onRetrieveItemSuccess(result as Item)
                },
                {
                    onRetrieveItemError()
                }
            )
//        subscription = itemDao.getById(itemId)
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(
//                        {
//                                result -> onRetrieveItemSuccess(result as Item)
//                        },
//                        {
//                            onRetrieveItemError()
//                        }
//                    )

    }

    private fun onRetrieveItemSuccess(item: Item) {
        showList.value = View.VISIBLE
        productListAdapter.updateItemList(item.products)
        itemName.value = item.name
    }

    private fun onRetrieveItemError(){
        showList.value = View.GONE
    }

}