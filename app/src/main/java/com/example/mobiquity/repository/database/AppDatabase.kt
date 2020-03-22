package com.example.mobiquity.repository.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mobiquity.repository.dao.ItemDao
import com.example.mobiquity.repository.model.Item

@Database(entities = [Item::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
        abstract fun itemDao(): ItemDao
}