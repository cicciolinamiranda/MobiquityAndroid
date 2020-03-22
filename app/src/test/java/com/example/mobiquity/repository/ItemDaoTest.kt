package com.example.mobiquity.repository

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.InstrumentationRegistry
import androidx.test.core.app.ApplicationProvider
import androidx.test.runner.AndroidJUnit4
import com.example.mobiquity.repository.dao.ItemDao
import com.example.mobiquity.repository.database.AppDatabase
import com.example.mobiquity.repository.model.Item
import com.example.mobiquity.repository.model.Product
import com.example.mobiquity.repository.model.SalePrice
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule


@RunWith(AndroidJUnit4::class)
class ItemDaoTest {

    lateinit var db : AppDatabase
    lateinit var itemDao : ItemDao

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setTheDatabase() {
        db = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
            AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        itemDao = db.itemDao()
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun insertItems() {
        val product = Product(1,1,"Product Name","/Bread.jpg", "Product Description", SalePrice(10.0, "EURO"))
        val products = arrayListOf(product)
        val item = Item(1, "Item Name", "Item Description", products)
        val insertedIds = itemDao.insertAll(arrayListOf(item))
        Assert.assertNotEquals(null, insertedIds)
        val itemResult = itemDao.getById(item.id)
        Assert.assertEquals(item, itemResult)

    }

    @Test
    fun getAllItems() {
        val product = Product(1,1,"Product Name","/Bread.jpg", "Product Description", SalePrice(10.0, "EURO"))
        val products = arrayListOf(product)
        val item = Item(1, "Item Name", "Item Description", products)
        itemDao.insertAll(arrayListOf(item))
        val returnItems = itemDao.all

        Assert.assertEquals(1, returnItems.size)
    }

    @Test
    fun getProductsFromAnItem() {
        val product = Product(1,1,"Product Name","/Bread.jpg", "Product Description", SalePrice(10.0, "EURO"))
        val product2 = Product(2,1,"Product Name","/Bread.jpg", "Product Description", SalePrice(10.0, "EURO"))
        val products = arrayListOf(product, product2)
        val item = Item(1, "Item Name", "Item Description", products)
        itemDao.insertAll(arrayListOf(item))
        val returnItems = itemDao.getProductsOnlyByItemId(1);
        val listType = object : TypeToken<List<Product>>() {}.type
        val productResult: List<Product>? = try { Gson().fromJson(returnItems.products, listType) } catch (e: NumberFormatException) { null }
        Assert.assertNotNull( productResult)
        Assert.assertEquals(2, productResult!!.size)

    }

}