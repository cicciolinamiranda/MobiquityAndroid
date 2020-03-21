package com.example.mobiquity.injection

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.mobiquity.repository.database.AppDatabase
import com.example.mobiquity.ui.item.ItemViewListModel
import com.example.mobiquity.ui.product.ProductViewListModel

class ViewListModelFactory(private val activity: AppCompatActivity): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val db = Room.databaseBuilder(activity.applicationContext, AppDatabase::class.java, "mobiquity.db").build()
        if (modelClass.isAssignableFrom(ItemViewListModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ItemViewListModel(db.itemDao()) as T
        }
        else if (modelClass.isAssignableFrom(ProductViewListModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductViewListModel(db.itemDao()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")

    }
}