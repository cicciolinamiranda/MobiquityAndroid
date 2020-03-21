package com.example.mobiquity.repository.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.mobiquity.repository.model.converters.Converters
import com.example.mobiquity.utils.TABLE_NAME_ITEM

@Entity(tableName = TABLE_NAME_ITEM)
@TypeConverters(Converters::class)
data class Item(
    @field:PrimaryKey
    val id: Long,
    val name: String,
    val description:String,
    val products: List<Product>
)