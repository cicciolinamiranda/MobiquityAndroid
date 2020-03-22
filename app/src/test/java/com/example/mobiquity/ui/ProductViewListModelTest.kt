package com.example.mobiquity.ui

import android.content.Context
import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.databinding.adapters.Converters
import androidx.room.Room
import com.example.mobiquity.RxImmediateSchedulerRule
import com.example.mobiquity.network.ItemApi
import com.example.mobiquity.repository.dao.ItemDao
import com.example.mobiquity.repository.database.AppDatabase
import com.example.mobiquity.repository.dto.ProductDTO
import com.example.mobiquity.repository.model.Item
import com.example.mobiquity.repository.model.Product
import com.example.mobiquity.repository.model.SalePrice
import com.example.mobiquity.ui.item.ItemViewListModel
import com.example.mobiquity.ui.product.ProductViewListModel
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit

class ProductViewListModelTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @get:Rule
    @JvmField
    val mockitoRule = MockitoJUnit.rule()!!

//    @get:Rule
//    @JvmField
//    val testSchedulerRule = RxImmediateSchedulerRule()

    @Mock
    lateinit var itemDao: ItemDao

    @Mock
    lateinit var db: AppDatabase

    @InjectMocks
    var productViewListModel = ProductViewListModel()

    @Before
    fun setup(){
        val context: Context = Mockito.mock(Context::class.java)
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        itemDao = db.itemDao()
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun getProductsSuccess() {
        val product = Product(1,1,"Product Name","/Bread.jpg", "Product Description", SalePrice(10.0, "EURO"))
        val products = arrayListOf(product)
        val item = Item(1, "Item Name", "Item Description", products)
        val gson = Gson()
        val productDTO = ProductDTO(item.name, gson.toJson(products))
        Mockito.`when`(itemDao.getProductsOnlyByItemId(item.id)).thenReturn(productDTO)
        productViewListModel.loadItems(item.id)

        Assert.assertNotEquals(null,productViewListModel.itemResp)
    }

    @Test
    fun getProductsFail() {
        Mockito.`when`(itemDao.getProductsOnlyByItemId(1)).thenReturn(null)
        productViewListModel.loadItems(1)

        Assert.assertNotEquals(null,productViewListModel.itemResp)
    }
}