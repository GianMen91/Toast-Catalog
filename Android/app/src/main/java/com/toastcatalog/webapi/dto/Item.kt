package com.toastcatalog.webapi.dto

import android.content.Context
import com.google.gson.Gson
import java.util.Date

// Data class to represent an item
data class Item(
    val id: Int,
    val name: String,
    val price: Double,
    val currency: String,
    val last_sold: String
)

// Saving items to a JSON file
fun saveItemsToFile(context: Context, items: List<Item>) {
    val gson = Gson()
    val jsonItems = gson.toJson(items)

    try {
        context.openFileOutput("items.json", Context.MODE_PRIVATE).use {
            it.write(jsonItems.toByteArray())
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

