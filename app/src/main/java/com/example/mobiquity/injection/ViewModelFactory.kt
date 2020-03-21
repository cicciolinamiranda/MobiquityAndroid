package com.example.mobiquity.injection

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.mobiquity.repository.database.AppDatabase
import com.example.mobiquity.ui.item.ItemViewListModel

class ViewModelFactory(private val activity: AppCompatActivity): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ItemViewListModel::class.java)) {
            val db = Room.databaseBuilder(activity.applicationContext, AppDatabase::class.java, "articles").build()
            @Suppress("UNCHECKED_CAST")
            return ItemViewListModel(db.itemDao()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")

    }
}