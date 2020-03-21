package com.example.mobiquity.repository.model.converters

import androidx.room.TypeConverter
import com.example.mobiquity.repository.model.Product
import com.example.mobiquity.repository.model.SalePrice
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    companion object {
        @TypeConverter
        @JvmStatic
        fun fromProductStr(list: List<Product>): String {
            val gson = Gson()
            return gson.toJson(list)
        }

        @TypeConverter
        @JvmStatic
        fun toProduct(productsStr: String): List<Product> {
            val listType = object : TypeToken<List<Product>>() {}.type
            return Gson().fromJson(productsStr, listType)
        }

        @TypeConverter
        @JvmStatic
        fun fromSalePrice(salePrice: SalePrice): String {
            val gson = Gson()
            return gson.toJson(salePrice)
        }

        @TypeConverter
        @JvmStatic
        fun toSalePriceString(salePriceStr: String): SalePrice {
            val salePrice = object : TypeToken<SalePrice>() {}.type
            return Gson().fromJson(salePriceStr, salePrice)
        }

    }

}