package com.toastcatalog.webapi

import android.content.ContentValues.TAG
import android.util.Log
import com.toastcatalog.webapi.dto.Item
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NetworkClient(private val onDataLoaded: (List<Item>?) -> Unit) {

    fun getItems() {
        val apiService = RetrofitClient.instance

        val call = apiService.getItems()
        call.enqueue(object : Callback<List<Item>> {
            override fun onResponse(call: Call<List<Item>>, response: Response<List<Item>>) {
                if (response.isSuccessful) {
                    val items = response.body()
                    onDataLoaded(items)
                } else {
                    onDataLoaded(null)
                    Log.e(TAG, "API error")
                }
            }

            override fun onFailure(call: Call<List<Item>>, t: Throwable) {
                onDataLoaded(null)
                Log.e(TAG, "Network request failed", t)
            }
        })
    }
}
