package com.example.mobiquity.repository.model

import androidx.room.TypeConverters
import com.example.mobiquity.repository.model.converters.Converters

@TypeConverters(Converters::class)
data class Product(
    val id: Long,
    val categoryId: Long,
    val name: String,
    val url: String,
    val description: String,
    val salePrice: SalePrice
)