package com.example.mobiquity

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import com.example.mobiquity.network.ItemApi
import com.example.mobiquity.repository.dao.ItemDao
import com.example.mobiquity.repository.database.AppDatabase
import com.example.mobiquity.repository.model.Item
import com.example.mobiquity.repository.model.Product
import com.example.mobiquity.repository.model.SalePrice
import com.example.mobiquity.ui.item.ItemViewListModel
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit


class ItemViewListModelTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @get:Rule @JvmField
    val mockitoRule = MockitoJUnit.rule()!!

//    @get:Rule @JvmField
//    val testSchedulerRule = RxImmediateSchedulerRule()

    @Mock
    lateinit var itemApi: ItemApi

    @Mock
    lateinit var itemDao: ItemDao

    @Mock
    lateinit var db: AppDatabase

    private var testItems : Observable<List<Item>>? = null

    @InjectMocks
    var itemViewListModel = ItemViewListModel()

    @Before
    fun setup(){
        val context: Context = mock(Context::class.java)
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        itemDao = db.itemDao()
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun getItemsFromCacheSuccess() {
        val product = Product(1,1,"Product Name","/Bread.jpg", "Product Description", SalePrice(10.0, "EURO"))
        val products = arrayListOf(product)
        val item = Item(1, "Item Name", "Item Description", products)
        val items = arrayListOf(item)

        testItems = Observable.just(items)
        Mockito.`when`(itemDao.all).thenReturn(items)
        itemViewListModel.loadItems()

        Assert.assertNotEquals(0,itemViewListModel.itemListAdapter.itemCount)

    }

    @Test
    fun getItemsFromApiSuccess() {
        val product = Product(1,1,"Product Name","/Bread.jpg", "Product Description", SalePrice(10.0, "EURO"))
        val products = arrayListOf(product)
        val item = Item(1, "Item Name", "Item Description", products)
        val items = arrayListOf(item)

        testItems = Observable.just(items)

        //Empty from db
        Mockito.`when`(itemDao.all).thenReturn(ArrayList())
        Mockito.`when`(itemApi.getItems()).thenReturn(testItems)
        itemViewListModel.loadItems()

        Assert.assertNotEquals(0,itemViewListModel.itemListAdapter.itemCount)
    }

    @Test
    fun getItemsFromCacheFail() {
        Mockito.`when`(itemDao.all).thenReturn(ArrayList())
        itemViewListModel.loadItems()

        Assert.assertEquals(0,itemViewListModel.itemListAdapter.itemCount)

    }

    @Test
    fun getItemsFromApiFail() {
        Mockito.`when`(itemDao.all).thenReturn(ArrayList())
        Mockito.`when`(itemApi.getItems()).thenReturn(null)
        itemViewListModel.loadItems()

        Assert.assertEquals(0,itemViewListModel.itemListAdapter.itemCount)
    }
}

