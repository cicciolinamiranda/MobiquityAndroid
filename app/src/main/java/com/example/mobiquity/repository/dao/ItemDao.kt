package com.example.mobiquity.repository.dao

import androidx.room.*
import com.example.mobiquity.repository.model.Item
import com.example.mobiquity.repository.model.converters.Converters
import com.example.mobiquity.utils.TABLE_NAME_ITEM

@Dao
@TypeConverters(Converters::class)
interface ItemDao {
    @get:Query("SELECT * FROM "+TABLE_NAME_ITEM)
    val all: List<Item>

    @Insert
    fun insert(vararg item: Item)

    @Transaction
    @Insert
    fun insertAll(items: List<Item>)
}