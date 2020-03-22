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

    @get:Rule @JvmField
    val testSchedulerRule = RxImmediateSchedulerRule()

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
        itemViewListModel = ItemViewListModel(itemDao)
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun getItemsSuccess() {
        val product = Product(1,1,"Product Name","/Bread.jpg", "Product Description", SalePrice(10.0, "EURO"))
        val products = arrayListOf(product)
        val item = Item(1, "Item Name", "Item Description", products)
        val items = arrayListOf(item)

        testItems = Observable.just(items)
        Mockito.`when`(itemDao.all).thenReturn(items)
        Mockito.`when`(itemApi.getItems()).thenReturn(testItems)
//        itemViewListModel = ItemViewListModel(itemDao)
        itemViewListModel.loadItems()

        Assert.assertEquals(1, itemViewListModel.itemListAdapter.itemCount)

        Assert.assertEquals(null,itemViewListModel.errorMessage.value)

        Assert.assertEquals(false,itemViewListModel.loadingVisibility.value)
    }
}

