package com.example.mobiquity.ui.item

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.example.mobiquity.R
import com.example.mobiquity.base.BaseViewModel
import com.example.mobiquity.network.ItemApi
import com.example.mobiquity.repository.dao.ItemDao
import com.example.mobiquity.repository.model.Item
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ItemViewListModel(private val itemDao: ItemDao): BaseViewModel(){
    @Inject
    lateinit var itemApi: ItemApi
    val itemListAdapter: ItemListAdapter = ItemListAdapter()

    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMessage:MutableLiveData<Int> = MutableLiveData()
    val errorClickListener = View.OnClickListener { loadItems() }

    private lateinit var subscription: Disposable

    init{
        loadItems()
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }


    private fun loadItems(){
        subscription = Observable.fromCallable { itemDao.all }
            .concatMap {
                    itemList ->
                if(itemList.isEmpty())
                    itemApi.getItems().concatMap {
                            apiItemResponse -> itemDao.insertAll(apiItemResponse)
                        Observable.just(apiItemResponse)
                    }
                else
                    Observable.just(itemList)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveArticleListStart() }
            .doOnTerminate { onRetrieveArticleListFinish() }
            .subscribe(
                { result -> onRetrieveArticleListSuccess(result as List<Item>) },
                { onRetrieveArticleListError() }
            )
    }

    private fun onRetrieveArticleListStart(){
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = null
    }

    private fun onRetrieveArticleListFinish(){
        loadingVisibility.value = View.GONE
    }


    private fun onRetrieveArticleListSuccess(itemList:List<Item>){
        itemListAdapter.updateItemList(itemList)
    }

    private fun onRetrieveArticleListError(){
        errorMessage.value = R.string.post_error
    }
}